package com.ronhan.pacypay.scheduler;

import com.google.common.collect.Streams;
import com.querydsl.core.Tuple;
import com.ronhan.Currency;
import com.ronhan.Geocodes;
import com.ronhan.iso8583.DoubleUtils;
import com.ronhan.iso8583.discover.sftp.SftpUtil;
import com.ronhan.iso8583.discover.sftp.files.Recap;
import com.ronhan.pacypay.pojo.entity.*;
import com.ronhan.pacypay.service.SettlementService;
import com.ronhan.pacypay.service.TransRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/29
 **/
@Slf4j
public class DailyRecap {

    @Autowired
    private TransRecordService transRecordService;

    @Autowired
    private SettlementService settlementService;

    @Value("${discover.sftp.host}")
    private String host;

    @Value("${discover.sftp.port}")
    private int port;

    @Value("${discover.sftp.username}")
    private String username;

    @Value("${discover.dxs_code}")
    private String dxsCode;

    @Value("${discover.env}")
    private String env;

    @Value("${discover.sftp.pubKey}")
    private String pub;

    @Value("${discover.sftp.priKey}")
    private String pri;

    @Value("${discover.aqgeo}")
    private String aqgeo;

    @Value("${discover.dRate}")
    private String dRate;

    @Value("${discover.settleCur}")
    private String settleCur;

    @Value("${discover.defaultPremium:0.05}")
    private Double defaultPremium;

    @Autowired
    private RedissonClient redisson;

    @Autowired
    private RecapUpload recapUpload;

    private ConcurrentHashMap<String, FxRate> rateHashMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
    }

    @Scheduled(cron = "0 0 1 ? * MON-FRI")
    public void produce() {
        String closed = redisson.<String>getBucket("dailyRecap").get();
        if ("true".equals(closed)) {
            log.info("interchange 已关闭");
            return;
        }

        //获取最后跑批日期
        RecapRecord recapRecord = settlementService.getLastRecapRecord();
        LocalDate d;
        if (recapRecord == null) {
            d = LocalDate.now().minusDays(1);
        } else {
            String recapDate = recapRecord.getRecapDate();
            d = LocalDate.parse(recapDate, DateTimeFormatter.ofPattern("ddMMyy"));
        }

        LocalDateTime start = LocalDateTime.of(d, LocalTime.MIN);

        LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX);

        exeRecap(start, end);
    }

    public void exeRecap(LocalDateTime start, LocalDateTime end) {
        log.info("【interchange file 批处理】: {}-{}", start, end);

        //load rate
        loadRate();

        List<Tuple> list = transRecordService.countUnSettledTrans(start, end);

        if (!CollectionUtils.isEmpty(list)) {
            List<String> lines = new ArrayList<>();

            for (Tuple t : list) {
                QTransRecord q = QTransRecord.transRecord;
                String dxs = t.get(q.issuerDxs);
                //String cur = t.get(q.currency);
                if (dxs == null) {
                    dxs = "ZX";
                }

                log.info("DXS code, count : {}", t);

                Long cnt = t.get(q.count());

                long numOfRecap = cnt % 1000 == 0 ? cnt / 1000 : cnt / 1000 + 1;

                for (int i = 1; i <= numOfRecap; i++) {
                    long offset = (i - 1) * 1000L;
                    long limit = Math.min(cnt - offset, 1000L);
                    List<TransRecord> transRecordList = transRecordService.getTrans(start, end, dxs, null, offset, limit);
                    //check refund, additional data
                    compose(dxs, null, transRecordList, lines);
                    transRecordService.updateTrans(transRecordList);
                }
            }

            String filename = env + ".DCINT" + dxsCode + ".DCIINTI." + dxsCode + "INTIN."
                    + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                    + ".N"
                    + LocalTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

            try {
                String dir = System.getProperty("user.dir");

                FileUtils.writeLines(new File(dir + File.separator + "OUT" + File.separator + filename), StandardCharsets.US_ASCII.displayName(), lines);
                FileUtils.writeLines(new File(dir + File.separator + "OUT" + File.separator + filename + ".SENT"), Collections.EMPTY_LIST);
            } catch (IOException e) {
                log.error("", e);
            }

            try {
                recapUpload.sendFile(filename, lines);
            } catch (Exception ex) {
                log.error("上传文件失败" + filename, ex);
                recapUpload.sentMail(filename);
            }

        }
    }

    @Scheduled(cron = "0 0 */8 * * ?")
    public void consume() {
        log.info("【下载对账文件】");
        List<String> files = SftpUtil.readDir(host, port, "DCOUT" + username, null, pri, pub, null, "./");

        String dir = System.getProperty("user.dir");
        List<String> exists = null;
        Path path = Paths.get(dir, "INT");
        Path subDir = path.resolve("load2db");
        try {
            File file = path.toFile();
            if (!file.exists()) {
                file.mkdirs();
            }

            if (!subDir.toFile().exists()) {
                subDir.toFile().mkdirs();
            }
            exists = Streams.concat(Files.list(path), Files.list(subDir))
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (files != null) {
            List<String> finalExists = exists;
            String outputDir = Paths.get(dir, "INT").toFile().getName();
            List<String> filesToDownload = files.stream()
                    .filter(n -> !finalExists.contains(n))
                    .collect(Collectors.toList());
            if (!filesToDownload.isEmpty()) {
                log.info("下载文件：{}", filesToDownload);
                SftpUtil.download(host, port, "DCOUT" + username, null, pri, pub, null, filesToDownload, outputDir);
            }

            //处理文件
            try {
                Files.list(path)
                        .map(Path::toFile)
                        .filter(File::isFile)
                        .map(File::getName)
                        .filter(f -> f.contains("ACQHO") || f.contains("DFFHO"))
                        .collect(Collectors.toList())
                        .forEach(f -> {
                            try {
                                settlementService.loadFile(path, f);
                            } catch (IOException e) {
                                log.error("", e);
                            }
                        });
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }

    private void compose(String dxs, String cur, List<TransRecord> records, List<String> lines) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setMaximumIntegerDigits(13);
        nf.setGroupingUsed(false);

        NumberFormat nf1 = NumberFormat.getInstance();
        nf1.setMaximumFractionDigits(3);
        nf1.setMaximumIntegerDigits(12);
        nf1.setGroupingUsed(false);

        String rcpdt = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
        Recap.RecapHeader recapHeader = new Recap.RecapHeader();
        //String currency = Currency.curD.get(cur);
        recapHeader.setSFTER(dxsCode);
        recapHeader.setDFTER(dxs);
        recapHeader.setCURKY(settleCur);
        recapHeader.setRCPDT(rcpdt);

        Integer recapNo = transRecordService.getRecapNo(recapHeader.getSFTER(), recapHeader.getDFTER());
        if (recapNo == null || recapNo == 999) {
            recapHeader.setRCPNO("001");
        } else {
            recapHeader.setRCPNO(String.format("%03d", recapNo + 1));
        }

        Recap.RecapTrailer recapTrailer = new Recap.RecapTrailer();
        recapTrailer.setSFTER(recapHeader.getSFTER());
        recapTrailer.setRCPNO(recapHeader.getRCPNO());
        recapTrailer.setDFTER(recapHeader.getDFTER());

        NumberFormat nf2 = NumberFormat.getInstance();
        nf2.setMaximumFractionDigits(3);
        nf2.setMinimumFractionDigits(2);
        nf2.setMaximumIntegerDigits(2);
        nf2.setGroupingUsed(false);
        recapTrailer.setDRATE(nf2.format(Double.parseDouble(dRate)));

        List<Triple<Recap.BatchHeader, List<Recap.ChargeDetail>, Recap.BatchTrailer>> batchs = composeBatch(records, recapHeader, recapTrailer, nf, nf1);


        lines.add(recapHeader.toString());
        batchs.stream().forEach(batch -> {
            lines.add(batch.getLeft().toString());
            lines.addAll(batch.getMiddle().stream().map(Recap.ChargeDetail::toString).collect(Collectors.toList()));
            lines.add(batch.getRight().toString());
        });
        lines.add(recapTrailer.toString());

        //save db
        RecapRecord recapRecord = transRecordService.saveRecap(recapHeader, recapTrailer);
        batchs.stream().forEach(batch -> {
            BatchRecord batchRecord = transRecordService.saveBatch(batch.getRight(), recapRecord);
            transRecordService.saveBatchDetail(batch.getMiddle(), batchRecord);
        });

        try {
            TimeUnit.SECONDS.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private List<Triple<Recap.BatchHeader, List<Recap.ChargeDetail>, Recap.BatchTrailer>> composeBatch(List<TransRecord> records, Recap.RecapHeader recapHeader, Recap.RecapTrailer recapTrailer, NumberFormat nf, NumberFormat nf1) {
        List<Triple<Recap.BatchHeader, List<Recap.ChargeDetail>, Recap.BatchTrailer>> list = new ArrayList();

        int countOfRecords = records.size();
        int countOfBatch = countOfRecords % 50 == 0 ? countOfRecords / 50 : countOfRecords / 50 + 1;

        int countOfCredit = 0;
        double amountOfCredit = 0;
        int countOfDebit = 0;
        double amountOfDebit = 0;

        for (int i = 1; i <= countOfBatch; i++) {

            String batchNo = String.format("%03d", i);
            Recap.BatchHeader batchHeader = new Recap.BatchHeader();
            batchHeader.setSFTER(recapHeader.getSFTER());
            batchHeader.setRCPNO(recapHeader.getRCPNO());
            batchHeader.setDFTER(recapHeader.getDFTER());
            batchHeader.setBATCH(batchNo);
            batchHeader.setRCPDT(recapHeader.getRCPDT());

            Recap.BatchTrailer batchTrailer = new Recap.BatchTrailer();
            batchTrailer.setSFTER(batchHeader.getSFTER());
            batchTrailer.setRCPNO(batchHeader.getRCPNO());
            batchTrailer.setDFTER(batchHeader.getDFTER());
            batchTrailer.setBATCH(batchHeader.getBATCH());

            int from = (i - 1) * 50;
            int end = 0;
            if (i != countOfBatch) {
                end = from + 50;
            } else {
                end = countOfRecords;
            }

            List<TransRecord> oneBatch = records.subList(from, end);
            List<Recap.ChargeDetail> chargeDetails = composeDetail(oneBatch, batchHeader, batchTrailer, nf);

            countOfCredit += Integer.parseInt(batchTrailer.getBTNCR());
            amountOfCredit = DoubleUtils.sum(amountOfCredit, Double.parseDouble(batchTrailer.getBTACR()));
            countOfDebit += Integer.parseInt(batchTrailer.getBTNDR());
            amountOfDebit = DoubleUtils.sum(amountOfDebit, Double.parseDouble(batchTrailer.getBTADR()));

            list.add(Triple.of(batchHeader, chargeDetails, batchTrailer));
        }

        recapTrailer.setRCNCR(String.valueOf(countOfCredit));
        recapTrailer.setRCACR(nf.format(amountOfCredit));
        recapTrailer.setRCNDR(String.valueOf(countOfDebit));
        recapTrailer.setRCADR(nf.format(amountOfDebit));

        double rnamt = DoubleUtils.mul(DoubleUtils.sub(Double.parseDouble(recapTrailer.getRCADR()), Double.parseDouble(recapTrailer.getRCACR())), DoubleUtils.sub(1D, DoubleUtils.div(Double.parseDouble(recapTrailer.getDRATE()), 5, 100D)));

        recapTrailer.setRNAMT(nf1.format(rnamt));
        return list;
    }

    private List<Recap.ChargeDetail> composeDetail(List<TransRecord> oneBatch, Recap.BatchHeader batchHeader, Recap.BatchTrailer batchTrailer, NumberFormat nf) {
        List<Recap.ChargeDetail> chargeDetails = new ArrayList<>();

        int countOfCredit = 0;
        double amountOfCredit = 0;
        int countOfDebit = 0;
        double amountOfDebit = 0;

        for (int i = 1; i <= oneBatch.size(); i++) {
            TransRecord e = oneBatch.get(i - 1);
            //原始交易金额,币种
            Double originAmount = e.getAmount();
            String transCurr = Currency.curD.get(e.getCurrency());

            Double amountInSettleCurr;
            if (!transCurr.equals(settleCur)) {
                //汇率
                FxRate rate = rateHashMap.get(transCurr + "-" + settleCur);
                if (rate == null) {
                    throw new RuntimeException("缺少汇率" + transCurr + "-" + settleCur);
                }
                //汇率溢价
                Double premium = rate.getPremium();
                if (premium == null || premium > 0.05 || premium < -0.05) {
                    premium = defaultPremium;
                }
                amountInSettleCurr = DoubleUtils.mul(originAmount, DoubleUtils.mul(rate.getRate(), DoubleUtils.sum(1.0, premium)));
            } else {
                amountInSettleCurr = originAmount;
            }

            Recap.ChargeDetail detail = new Recap.ChargeDetail();
            detail.setTransId(e.getId());
            detail.setSFTER(batchHeader.getSFTER());
            detail.setRCPNO(batchHeader.getRCPNO());
            detail.setDFTER(batchHeader.getDFTER());
            detail.setBATCH(batchHeader.getBATCH());
            detail.setSEQNO(String.format("%03d", i));
            detail.setACCT(e.getCardNo());
            detail.setCAMTR(nf.format(amountInSettleCurr));
            detail.setCHGDT(e.getTransactionTime().substring(0, 6));
            detail.setDATYP("TS");
            if (StringUtils.isNotBlank(e.getChargeType())) {
                detail.setCHTYP(e.getChargeType());
            } else {
                detail.setCHTYP("999");
            }
            detail.setESTAB(e.getAcceptorName());
            detail.setLCITY(e.getAcceptorCity());
            detail.setGEOCD(e.getOriginatorCountry());

            if (e.getType() == 1) {
                detail.setAPPCD(e.getActionCode());
                detail.setTYPCH("TI");
                countOfDebit++;
                amountOfDebit = DoubleUtils.sum(amountOfDebit, Double.parseDouble(detail.getCAMTR()));
            } else if (e.getType() == 2) {
                detail.setTYPCH("TJ");
                countOfCredit++;
                amountOfCredit = DoubleUtils.sum(amountOfCredit, Double.parseDouble(detail.getCAMTR()));
            }
            detail.setREFNO(RandomStringUtils.randomAlphanumeric(8));
            detail.setANBR(e.getApprovalCode());
            detail.setSENUM(e.getAcceptorId());
            detail.setINTES(e.getIntes());
            detail.setESTST(e.getAcceptorStreet());
            detail.setESTCO(StringUtils.toRootUpperCase(Geocodes.getCountry(e.getAcceptorCountry())));
            detail.setESTZP(e.getPostalCode());
            //detail.setESTPN();
            detail.setMCCCD(e.getMcc());
            if (e.getType() == 2) {
                detail.setORIGD(e.getReferenceId());
            }
            detail.setCHOLDP("9");
            detail.setCARDP("0");
            if (StringUtils.isEmpty(e.getDe22())) {
                detail.setCPTRM("1");
            } else {
                detail.setCPTRM(e.getDe22().substring(6, 7));
            }
            if (StringUtils.isNotBlank(e.getEci())) {
                detail.setECI(e.getEci());
            }
            if (StringUtils.isNotBlank(e.getCavv())) {
                detail.setCAVV(e.getCavv());
            }
            detail.setNRID(e.getReferenceId());
            detail.setCRDINP("1");
            //detail.setSURFEE();
            detail.setTRMTYP("M");
            detail.setAQGEO(aqgeo);

            if ("2".equals(e.getAuthType())) {
                detail.setCVVRST(e.getCavvValidationRes());
                detail.setAUTYP(e.getAuthType());
                detail.setAURCDE(e.getThreeDSAuthResCode());
                detail.setSECFAR(e.getSecondFacAuthResCode());
                detail.setCVVIND(e.getCavvKeyIndicator());
                detail.setAUTHTR(e.getAuthTrackingNum());
                detail.setVERACT(e.getVersionAndAuthAction());
                detail.setIPADDR(e.getIpHex());
            }
            //detail.setSCAEXE();
            //detail.setTRAIND();
            //detail.setORNRID();

            chargeDetails.add(detail);
        }

        batchTrailer.setBTNCR(String.valueOf(countOfCredit));
        batchTrailer.setBTACR(nf.format(amountOfCredit));
        batchTrailer.setBTNDR(String.valueOf(countOfDebit));
        batchTrailer.setBTADR(nf.format(amountOfDebit));

        return chargeDetails;
    }

    private void loadRate() {
        Set<String> curs = Currency.curC.keySet();
        curs.forEach(cur -> {
            if (!cur.equals(settleCur)) {
                FxRate rate = settlementService.loadRate(cur, settleCur);
                if (rate != null) {
                    rateHashMap.put(cur + "-" + settleCur, rate);
                }
            }
        });
    }
}
