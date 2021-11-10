package com.ronhan.iso8583;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/25
 **/
public class DateUtils {
    public static DateTimeFormatter MMddHHmmss = DateTimeFormatter.ofPattern("MMddHHmmss");
    public static DateTimeFormatter yyMMddHHmmss = DateTimeFormatter.ofPattern("yyMMddHHmmss");
    public static DateTimeFormatter yyyy_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(DateTimeFormatter formatter) {
        return format(LocalDateTime.now(), formatter);
    }

    public static String format(DateTimeFormatter formatter, TimeZone timeZone) {
        return format(LocalDateTime.now(timeZone.toZoneId()), formatter);
    }

    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        return dateTime.format(formatter);
    }

    public static LocalDateTime parse(String dateTime, DateTimeFormatter formatter) {
        return LocalDateTime.from(formatter.parse(dateTime));
    }
}
