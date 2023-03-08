package com.ronhan.pacypay.service;

import com.ronhan.pacypay.pojo.FxRateQuery;
import com.ronhan.pacypay.pojo.PageResult;
import com.ronhan.pacypay.pojo.entity.CurrencyConfig;
import com.ronhan.pacypay.pojo.entity.FxRate;
import com.ronhan.pacypay.pojo.entity.RecapRecord;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/8/27
 **/
public interface SettlementService {

    /**
     * 结算文件入库
     * @param path
     * @param filename
     */
    void loadFile(Path path, String filename) throws IOException;

    /**
     * 载入汇率
     * @return
     */
    FxRate loadRate(String sellCurrency, String buyCurrency);

    /**
     * 获取最后一次跑批
     * @return
     */
    RecapRecord getLastRecapRecord();

    /**
     * 保存汇率
     * @param rates
     */
    void saveRate(List<FxRate> rates);

    /**
     * 查询汇率
     * @return
     */
    PageResult<FxRate> list(FxRateQuery query);

    /**
     * 发送解析的对账文件
     */
    void sendParsedFile();

    /**
     * 结算币种配置
     * @return
     */
    List<CurrencyConfig> listCurrencyConfig();
}
