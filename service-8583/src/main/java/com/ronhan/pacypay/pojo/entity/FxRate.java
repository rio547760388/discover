package com.ronhan.pacypay.pojo.entity;

import com.ronhan.pacypay.pojo.FxRateDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/9/28
 **/
@Data
@Entity
@Table(name = "FX_RATE")
@NoArgsConstructor
@AllArgsConstructor
public class FxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "processing_date")
    private LocalDate processingDate;

    @Column(name = "sell_currency")
    private String sellCurrency;

    @Column(name = "buy_currency")
    private String buyCurrency;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "settlement_date")
    private LocalDate settlementDate;

    @Column(name = "opt_time")
    private LocalDateTime optTime;

    /**
     * 溢价
     */
    @Column(name = "premium")
    private Double premium;

    static Pattern p = Pattern.compile("\\p{Alpha}+");

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yy", Locale.ENGLISH);

    public static FxRate parse(String date1, String cur1, String cur2, String rate, String date2, LocalDateTime optTime) {
        return new FxRate(null, parseDate(date1), cur1, cur2, Double.parseDouble(rate), parseDate(date2), optTime, null);
    }

    public static FxRate parseOld(FxRateDto fxRateDto, LocalDateTime optTime) {
        return new FxRate(null, fxRateDto.getProcessingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                fxRateDto.getSellCurrency(),
                fxRateDto.getBuyCurrency(),
                fxRateDto.getRate(),
                fxRateDto.getSettlementDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                optTime,
                null);
    }

    public static LocalDate parseDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }

        Matcher m = p.matcher(date);
        if (m.find()) {
            String month = m.group();
            String cap = StringUtils.capitalize(month.toLowerCase());
            date = date.replace(month, cap);
            return LocalDate.parse(date, formatter);
        }
        return null;
    }

}
