package com.ronhan.pacypay.endpoint;

import com.ronhan.crypto.Crypto;
import com.ronhan.iso8583.DateUtils;
import com.ronhan.iso8583.discover.sftp.Sftp;
import com.ronhan.iso8583.discover.sftp.SftpUtil;
import com.ronhan.pacypay.constants.ProgramEnvConstant;
import com.ronhan.pacypay.pojo.FxRateDto;
import com.ronhan.pacypay.pojo.FxRateQuery;
import com.ronhan.pacypay.pojo.PageResult;
import com.ronhan.pacypay.pojo.Response;
import com.ronhan.pacypay.pojo.entity.FxRate;
import com.ronhan.pacypay.scheduler.DailyRecap;
import com.ronhan.pacypay.service.SettlementService;
import com.ronhan.pacypay.util.DcFileWriter;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/7/13
 **/
@RestController
@Api(tags = "")
@Slf4j
public class GenerateRecapEndpoint implements ApplicationContextAware {

    private ApplicationContext ac;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ac = applicationContext;
    }

    @GetMapping("generate")
    public Response generate(String start, String end) {
        LocalDateTime t1 = DateUtils.parse(start, DateUtils.yyyy_MM_DD_HH_MM_SS);
        LocalDateTime t2 = DateUtils.parse(end, DateUtils.yyyy_MM_DD_HH_MM_SS);
        DailyRecap dailyRecap = ac.getBean(DailyRecap.class);
        if (dailyRecap != null) {
            dailyRecap.exeRecap(t1, t2);
            return Response.ok("success");
        } else {
            return Response.error(400, "fail");
        }
    }

    @GetMapping("download")
    public Response download() {
        DailyRecap dailyRecap = ac.getBean(DailyRecap.class);
        if (dailyRecap != null) {
            dailyRecap.consume();
            return Response.ok("success");
        } else {
            return Response.error(400, "fail");
        }
    }

    @PostMapping("upload")
    public Response uploadFxRate(HttpServletRequest req, @RequestParam("file") MultipartFile file) throws IOException {
        Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
        CSVParser parser = new CSVParser(reader, CSVFormat.EXCEL.withHeader("Processing Date", "Sell Currency", "Buy Currency", "Rate", "Settlement Date"));

        LocalDateTime optTime = LocalDateTime.now();

        ArrayList<FxRate> rates = new ArrayList<>();
        parser.getRecords().forEach(r -> {
            if (r.getRecordNumber() != 1) {
                FxRate rate = FxRate.parse(r.get("Processing Date"),
                        r.get("Sell Currency"),
                        r.get("Buy Currency"),
                        r.get("Rate"),
                        r.get("Settlement Date"), optTime);

                rates.add(rate);
            }
        });

        settlementService.saveRate(rates);

        return Response.ok(null);
    }

    @PostMapping("fxRates")
    public Response<PageResult<FxRate>> listFxRate(@RequestBody FxRateQuery query) {
        return Response.ok(settlementService.list(query));
    }

    @PostMapping("saveFxRate")
    public Response uploadFxRateList(@RequestBody List<FxRateDto> list) {
        LocalDateTime optTime = LocalDateTime.now();

        ArrayList<FxRate> rates = new ArrayList<>();
        list.forEach(r -> {

            FxRate rate = FxRate.parseOld(r,
                    optTime);

            rates.add(rate);

        });

        settlementService.saveRate(rates);

        return Response.ok(null);
    }

    @GetMapping("sendToDc")
    public Response sendToDc(@RequestParam("filename") String filename) {
        String dir = ProgramEnvConstant.baseDir;

        File file = new File(dir + File.separator + "OUT" + File.separator + filename);
        if (file.exists()) {
            Sftp<Void> sftp = Sftp.builder().host(host)
                    .port(port)
                    .username("DCINT" + username)
                    .privateKeyEntry(Crypto.getPrivateKeyEntry())
                    .build();
            sftp.setHandler(
                    c -> {
                        try (OutputStream os = c.write(filename, SftpUtil.openModes); FileInputStream is = FileUtils.openInputStream(file)) {
                            IOUtils.copy(is, os);
                        } catch (IOException ioe) {
                            log.error("上传文件失败" + filename, ioe);
                        }
                        return null;
                    }
            ).start();

        } else {
            return Response.ok("文件不存在");
        }
        return Response.ok("上传完成");
    }

    @GetMapping("fileList")
    public Response<List<String>> getFileList() {
        String dir = ProgramEnvConstant.baseDir;

        File file = new File(dir + File.separator + "INT");

        if (file.exists() && file.isDirectory()) {
            List<String> filenames = Arrays.stream(Objects.requireNonNull(file.listFiles((d, filename) -> filename.contains("ACQHO") || filename.contains("DFFHO") || filename.contains("load2db"))))
                    .flatMap(f -> {
                        if (f.isFile()) {
                            return Stream.of(f);
                        } else if (f.isDirectory()) {
                            return Arrays.stream(Objects.requireNonNull(f.listFiles(File::isFile)));
                        }
                        return null;
                    })
                    .map(File::getAbsolutePath).collect(Collectors.toList());

            return Response.ok(filenames);
        }

        return Response.error(1, "文件不存在或未找到", null);
    }

    @GetMapping("downloadParsedFile")
    public void downloadParsedFile(String filename, HttpServletRequest req, HttpServletResponse res) {
        String dir = ProgramEnvConstant.baseDir;

        File file = new File(dir + File.separator + "INT");

        File target = new File(file, filename);

        if (!target.exists()) {
            target = new File(file, "load2db" + File.separator + filename);
        }

        if (target.exists()) {
            List<String> list = null;
            try {
                list = Files.readAllLines(target.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            res.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            res.setHeader("Content-Disposition", "attachment;filename=" + filename + ".csv");

            try (OutputStream os = res.getOutputStream()) {
                if (filename.contains("ACQHO")) {
                    try {
                        DcFileWriter.writeInterchangeConfirmation(list, os);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (filename.contains("DFFHO")) {
                    try {
                        DcFileWriter.writeDisputeAndFee(list, os);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    res.setHeader("Content-Disposition", "attachment;filename=" + filename);

                    IOUtils.writeLines(list, null, os);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }

            return;
        }

        res.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
