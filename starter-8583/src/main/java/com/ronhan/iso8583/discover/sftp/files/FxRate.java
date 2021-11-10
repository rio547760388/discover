package com.ronhan.iso8583.discover.sftp.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/6/30
 **/
@Data
@AllArgsConstructor
public class FxRate {
    private String processingDate;

    private String sellCurrency;

    private String buyCurrency;

    private String rate;

    private String settlementDate;

    public static List<FxRate> parse(InputStream is) throws IOException {
        CSVParser parser = new CSVParser(new InputStreamReader(is), CSVFormat.DEFAULT);
        return parser.getRecords()
                .stream()
                .map(e -> new FxRate(e.get(0), e.get(1), e.get(2), e.get(3), e.get(4)))
                .collect(Collectors.toList());
    }
}
