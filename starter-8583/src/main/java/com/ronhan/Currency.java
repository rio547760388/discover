package com.ronhan;


import com.ronhan.iso8583.DoubleUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/5/25
 **/
public class Currency {
    public static Map<String, String> curC = new HashMap<>();

    public static Map<String, String> curD = new HashMap<>();

    public static Map<String, Integer> curs = new HashMap<>();

    static {
        curC.put("FJD", "242");
        curC.put("MXN", "484");
        curC.put("STD", "678");
        curC.put("LVL", "428");
        curC.put("SCR", "690");
        curC.put("CDF", "976");
        curC.put("BBD", "052");
        curC.put("GTQ", "320");
        curC.put("CLP", "152");
        curC.put("HNL", "340");
        curC.put("UGX", "800");
        curC.put("ZAR", "710");
        curC.put("MXV", "979");
        curC.put("TND", "788");
        curC.put("STN", "930");
        curC.put("CUC", "931");
        curC.put("BSD", "044");
        curC.put("SLL", "694");
        curC.put("SDD", "736");
        curC.put("SDG", "938");
        curC.put("IQD", "368");
        curC.put("CUP", "192");
        curC.put("GMD", "270");
        curC.put("TWD", "901");
        curC.put("RSD", "941");
        curC.put("DOP", "214");
        curC.put("KMF", "174");
        curC.put("MYR", "458");
        curC.put("FKP", "238");
        curC.put("XOF", "952");
        curC.put("GEL", "981");
        curC.put("UYU", "858");
        curC.put("MAD", "504");
        curC.put("CVE", "132");
        curC.put("TOP", "776");
        curC.put("AZN", "944");
        curC.put("PGK", "598");
        curC.put("OMR", "512");
        curC.put("KES", "404");
        curC.put("SEK", "752");
        curC.put("BTN", "064");
        curC.put("UAH", "980");
        curC.put("GNF", "324");
        curC.put("ERN", "232");
        curC.put("MZN", "943");
        curC.put("SVC", "222");
        curC.put("MZM", "508");
        curC.put("ARS", "032");
        curC.put("QAR", "634");
        curC.put("IRR", "364");
        curC.put("MRO", "478");
        curC.put("CNY", "156");
        curC.put("XPF", "953");
        curC.put("THB", "764");
        curC.put("UZS", "860");
        curC.put("MRU", "929");
        curC.put("BDT", "050");
        curC.put("LYD", "434");
        curC.put("BMD", "060");
        curC.put("KWD", "414");
        curC.put("PHP", "608");
        curC.put("RUB", "643");
        curC.put("PYG", "600");
        curC.put("ISK", "352");
        curC.put("JMD", "388");
        curC.put("GWP", "624");
        curC.put("COP", "170");
        curC.put("USD", "840");
        curC.put("MKD", "807");
        curC.put("RUR", "810");
        curC.put("DZD", "012");
        curC.put("PAB", "590");
        curC.put("SGD", "702");
        curC.put("ETB", "230");
        curC.put("VEB", "862");
        curC.put("SOS", "706");
        curC.put("KGS", "417");
        curC.put("VUV", "548");
        curC.put("VEF", "937");
        curC.put("LAK", "418");
        curC.put("BND", "096");
        curC.put("XAF", "950");
        curC.put("LRD", "430");
        curC.put("HRK", "191");
        curC.put("CHF", "756");
        curC.put("ALL", "008");
        curC.put("DJF", "262");
        curC.put("CHE", "947");
        curC.put("GHC", "288");
        curC.put("MTL", "470");
        curC.put("VES", "928");
        curC.put("ZMW", "967");
        curC.put("TZS", "834");
        curC.put("ADP", "020");
        curC.put("VND", "704");
        curC.put("AUD", "036");
        curC.put("ILS", "376");
        curC.put("GHS", "936");
        curC.put("GYD", "328");
        curC.put("KPW", "408");
        curC.put("CHW", "948");
        curC.put("BOB", "068");
        curC.put("KHR", "116");
        curC.put("MDL", "498");
        curC.put("IDR", "360");
        curC.put("KYD", "136");
        curC.put("AMD", "051");
        curC.put("BWP", "072");
        curC.put("SHP", "654");
        curC.put("TRY", "949");
        curC.put("CYP", "196");
        curC.put("LBP", "422");
        curC.put("TJS", "972");
        curC.put("JOD", "400");
        curC.put("HKD", "344");
        curC.put("RWF", "646");
        curC.put("AED", "784");
        curC.put("EUR", "978");
        curC.put("LSL", "426");
        curC.put("DKK", "208");
        curC.put("BGL", "100");
        curC.put("CAD", "124");
        curC.put("BOV", "984");
        curC.put("BGN", "975");
        curC.put("EEK", "233");
        curC.put("MMK", "104");
        curC.put("NOK", "578");
        curC.put("MUR", "480");
        curC.put("SYP", "760");
        curC.put("ZWL", "932");
        curC.put("GIP", "292");
        curC.put("RON", "946");
        curC.put("YUM", "891");
        curC.put("ZWN", "942");
        curC.put("LKR", "144");
        curC.put("NGN", "566");
        curC.put("CRC", "188");
        curC.put("CZK", "203");
        curC.put("PKR", "586");
        curC.put("XCD", "951");
        curC.put("ANG", "532");
        curC.put("HTG", "332");
        curC.put("BHD", "048");
        curC.put("SIT", "705");
        curC.put("KZT", "398");
        curC.put("SRD", "968");
        curC.put("SZL", "748");
        curC.put("LTL", "440");
        curC.put("SAR", "682");
        curC.put("TTD", "780");
        curC.put("YER", "886");
        curC.put("MVR", "462");
        curC.put("AFN", "971");
        curC.put("INR", "356");
        curC.put("AWG", "533");
        curC.put("KRW", "410");
        curC.put("NPR", "524");
        curC.put("JPY", "392");
        curC.put("MNT", "496");
        curC.put("AOA", "973");
        curC.put("PLN", "985");
        curC.put("SBD", "090");
        curC.put("GBP", "826");
        curC.put("BYN", "933");
        curC.put("HUF", "348");
        curC.put("BYR", "974");
        curC.put("BIF", "108");
        curC.put("MWK", "454");
        curC.put("MGA", "969");
        curC.put("BZD", "084");
        curC.put("BAM", "977");
        curC.put("EGP", "818");
        curC.put("MOP", "446");
        curC.put("NAD", "516");
        curC.put("SSP", "728");
        curC.put("NIO", "558");
        curC.put("TMM", "795");
        curC.put("PEN", "604");
        curC.put("NZD", "554");
        curC.put("WST", "882");
        curC.put("CLF", "990");
        curC.put("BRL", "986");

        curD.put("242", "FJD");
        curD.put("484", "MXN");
        curD.put("678", "STD");
        curD.put("428", "LVL");
        curD.put("690", "SCR");
        curD.put("976", "CDF");
        curD.put("052", "BBD");
        curD.put("320", "GTQ");
        curD.put("152", "CLP");
        curD.put("340", "HNL");
        curD.put("800", "UGX");
        curD.put("710", "ZAR");
        curD.put("979", "MXV");
        curD.put("788", "TND");
        curD.put("930", "STN");
        curD.put("931", "CUC");
        curD.put("044", "BSD");
        curD.put("694", "SLL");
        curD.put("736", "SDD");
        curD.put("938", "SDG");
        curD.put("368", "IQD");
        curD.put("192", "CUP");
        curD.put("270", "GMD");
        curD.put("901", "TWD");
        curD.put("941", "RSD");
        curD.put("214", "DOP");
        curD.put("174", "KMF");
        curD.put("458", "MYR");
        curD.put("238", "FKP");
        curD.put("952", "XOF");
        curD.put("981", "GEL");
        curD.put("858", "UYU");
        curD.put("504", "MAD");
        curD.put("132", "CVE");
        curD.put("776", "TOP");
        curD.put("944", "AZN");
        curD.put("598", "PGK");
        curD.put("512", "OMR");
        curD.put("404", "KES");
        curD.put("752", "SEK");
        curD.put("064", "BTN");
        curD.put("980", "UAH");
        curD.put("324", "GNF");
        curD.put("232", "ERN");
        curD.put("943", "MZN");
        curD.put("222", "SVC");
        curD.put("508", "MZM");
        curD.put("032", "ARS");
        curD.put("634", "QAR");
        curD.put("364", "IRR");
        curD.put("478", "MRO");
        curD.put("156", "CNY");
        curD.put("953", "XPF");
        curD.put("764", "THB");
        curD.put("860", "UZS");
        curD.put("929", "MRU");
        curD.put("050", "BDT");
        curD.put("434", "LYD");
        curD.put("060", "BMD");
        curD.put("414", "KWD");
        curD.put("608", "PHP");
        curD.put("643", "RUB");
        curD.put("600", "PYG");
        curD.put("352", "ISK");
        curD.put("388", "JMD");
        curD.put("624", "GWP");
        curD.put("170", "COP");
        curD.put("840", "USD");
        curD.put("807", "MKD");
        curD.put("810", "RUR");
        curD.put("012", "DZD");
        curD.put("590", "PAB");
        curD.put("702", "SGD");
        curD.put("230", "ETB");
        curD.put("862", "VEB");
        curD.put("706", "SOS");
        curD.put("417", "KGS");
        curD.put("548", "VUV");
        curD.put("937", "VEF");
        curD.put("418", "LAK");
        curD.put("096", "BND");
        curD.put("950", "XAF");
        curD.put("430", "LRD");
        curD.put("191", "HRK");
        curD.put("756", "CHF");
        curD.put("008", "ALL");
        curD.put("262", "DJF");
        curD.put("947", "CHE");
        curD.put("288", "GHC");
        curD.put("470", "MTL");
        curD.put("928", "VES");
        curD.put("967", "ZMW");
        curD.put("834", "TZS");
        curD.put("020", "ADP");
        curD.put("704", "VND");
        curD.put("036", "AUD");
        curD.put("376", "ILS");
        curD.put("936", "GHS");
        curD.put("328", "GYD");
        curD.put("408", "KPW");
        curD.put("948", "CHW");
        curD.put("068", "BOB");
        curD.put("116", "KHR");
        curD.put("498", "MDL");
        curD.put("360", "IDR");
        curD.put("136", "KYD");
        curD.put("051", "AMD");
        curD.put("072", "BWP");
        curD.put("654", "SHP");
        curD.put("949", "TRY");
        curD.put("196", "CYP");
        curD.put("422", "LBP");
        curD.put("972", "TJS");
        curD.put("400", "JOD");
        curD.put("344", "HKD");
        curD.put("646", "RWF");
        curD.put("784", "AED");
        curD.put("978", "EUR");
        curD.put("426", "LSL");
        curD.put("208", "DKK");
        curD.put("100", "BGL");
        curD.put("124", "CAD");
        curD.put("984", "BOV");
        curD.put("975", "BGN");
        curD.put("233", "EEK");
        curD.put("104", "MMK");
        curD.put("578", "NOK");
        curD.put("480", "MUR");
        curD.put("760", "SYP");
        curD.put("932", "ZWL");
        curD.put("292", "GIP");
        curD.put("946", "RON");
        curD.put("891", "YUM");
        curD.put("942", "ZWN");
        curD.put("144", "LKR");
        curD.put("566", "NGN");
        curD.put("188", "CRC");
        curD.put("203", "CZK");
        curD.put("586", "PKR");
        curD.put("951", "XCD");
        curD.put("532", "ANG");
        curD.put("332", "HTG");
        curD.put("048", "BHD");
        curD.put("705", "SIT");
        curD.put("398", "KZT");
        curD.put("968", "SRD");
        curD.put("748", "SZL");
        curD.put("440", "LTL");
        curD.put("682", "SAR");
        curD.put("780", "TTD");
        curD.put("886", "YER");
        curD.put("462", "MVR");
        curD.put("971", "AFN");
        curD.put("356", "INR");
        curD.put("533", "AWG");
        curD.put("410", "KRW");
        curD.put("524", "NPR");
        curD.put("392", "JPY");
        curD.put("496", "MNT");
        curD.put("973", "AOA");
        curD.put("985", "PLN");
        curD.put("090", "SBD");
        curD.put("826", "GBP");
        curD.put("933", "BYN");
        curD.put("348", "HUF");
        curD.put("974", "BYR");
        curD.put("108", "BIF");
        curD.put("454", "MWK");
        curD.put("969", "MGA");
        curD.put("084", "BZD");
        curD.put("977", "BAM");
        curD.put("818", "EGP");
        curD.put("446", "MOP");
        curD.put("516", "NAD");
        curD.put("728", "SSP");
        curD.put("558", "NIO");
        curD.put("795", "TMM");
        curD.put("604", "PEN");
        curD.put("554", "NZD");
        curD.put("882", "WST");
        curD.put("990", "CLF");
        curD.put("986", "BRL");

        curs.put("242", 2);
        curs.put("484", 2);
        curs.put("678", 2);
        curs.put("428", 2);
        curs.put("690", 2);
        curs.put("976", 2);
        curs.put("052", 2);
        curs.put("320", 2);
        curs.put("152", 0);
        curs.put("340", 2);
        curs.put("800", 0);
        curs.put("710", 2);
        curs.put("979", 2);
        curs.put("788", 3);
        curs.put("930", 2);
        curs.put("931", 2);
        curs.put("044", 2);
        curs.put("694", 2);
        curs.put("736", 2);
        curs.put("938", 2);
        curs.put("368", 3);
        curs.put("192", 2);
        curs.put("270", 2);
        curs.put("901", 2);
        curs.put("941", 2);
        curs.put("214", 2);
        curs.put("174", 0);
        curs.put("458", 2);
        curs.put("238", 2);
        curs.put("952", 0);
        curs.put("981", 2);
        curs.put("858", 2);
        curs.put("504", 2);
        curs.put("132", 2);
        curs.put("776", 2);
        curs.put("944", 2);
        curs.put("598", 2);
        curs.put("512", 3);
        curs.put("404", 2);
        curs.put("752", 2);
        curs.put("064", 2);
        curs.put("980", 2);
        curs.put("324", 0);
        curs.put("232", 2);
        curs.put("943", 2);
        curs.put("222", 2);
        curs.put("508", 2);
        curs.put("032", 2);
        curs.put("634", 2);
        curs.put("364", 2);
        curs.put("478", 2);
        curs.put("156", 2);
        curs.put("953", 0);
        curs.put("764", 2);
        curs.put("860", 2);
        curs.put("929", 2);
        curs.put("050", 2);
        curs.put("434", 3);
        curs.put("060", 2);
        curs.put("414", 3);
        curs.put("608", 2);
        curs.put("643", 2);
        curs.put("600", 0);
        curs.put("352", 2);
        curs.put("388", 2);
        curs.put("624", 0);
        curs.put("170", 2);
        curs.put("840", 2);
        curs.put("807", 2);
        curs.put("810", 2);
        curs.put("012", 2);
        curs.put("590", 2);
        curs.put("702", 2);
        curs.put("230", 2);
        curs.put("862", 2);
        curs.put("706", 2);
        curs.put("417", 2);
        curs.put("548", 0);
        curs.put("937", 2);
        curs.put("418", 2);
        curs.put("096", 2);
        curs.put("950", 0);
        curs.put("430", 2);
        curs.put("191", 2);
        curs.put("756", 2);
        curs.put("008", 0);
        curs.put("262", 0);
        curs.put("947", 2);
        curs.put("288", 2);
        curs.put("470", 2);
        curs.put("928", 2);
        curs.put("967", 2);
        curs.put("834", 2);
        curs.put("020", 0);
        curs.put("704", 0);
        curs.put("036", 2);
        curs.put("376", 2);
        curs.put("936", 2);
        curs.put("328", 2);
        curs.put("408", 2);
        curs.put("948", 2);
        curs.put("068", 2);
        curs.put("116", 2);
        curs.put("498", 2);
        curs.put("360", 2);
        curs.put("136", 2);
        curs.put("051", 2);
        curs.put("072", 2);
        curs.put("654", 2);
        curs.put("949", 2);
        curs.put("196", 2);
        curs.put("422", 2);
        curs.put("972", 2);
        curs.put("400", 3);
        curs.put("344", 2);
        curs.put("646", 0);
        curs.put("784", 2);
        curs.put("978", 2);
        curs.put("426", 2);
        curs.put("208", 2);
        curs.put("100", 2);
        curs.put("124", 2);
        curs.put("984", 2);
        curs.put("975", 2);
        curs.put("233", 2);
        curs.put("104", 2);
        curs.put("578", 2);
        curs.put("480", 2);
        curs.put("760", 2);
        curs.put("932", 2);
        curs.put("292", 2);
        curs.put("946", 2);
        curs.put("891", 2);
        curs.put("942", 2);
        curs.put("144", 2);
        curs.put("566", 2);
        curs.put("188", 2);
        curs.put("203", 2);
        curs.put("586", 2);
        curs.put("951", 2);
        curs.put("532", 2);
        curs.put("332", 2);
        curs.put("048", 3);
        curs.put("705", 2);
        curs.put("398", 2);
        curs.put("968", 2);
        curs.put("748", 2);
        curs.put("440", 2);
        curs.put("682", 2);
        curs.put("780", 2);
        curs.put("886", 2);
        curs.put("462", 2);
        curs.put("971", 2);
        curs.put("356", 2);
        curs.put("533", 2);
        curs.put("410", 0);
        curs.put("524", 2);
        curs.put("392", 0);
        curs.put("496", 2);
        curs.put("973", 2);
        curs.put("985", 2);
        curs.put("090", 2);
        curs.put("826", 2);
        curs.put("933", 2);
        curs.put("348", 2);
        curs.put("974", 0);
        curs.put("108", 0);
        curs.put("454", 2);
        curs.put("969", 2);
        curs.put("084", 2);
        curs.put("977", 2);
        curs.put("818", 2);
        curs.put("446", 2);
        curs.put("516", 2);
        curs.put("728", 2);
        curs.put("558", 2);
        curs.put("795", 2);
        curs.put("604", 2);
        curs.put("554", 2);
        curs.put("882", 2);
        curs.put("990", 0);
        curs.put("986", 2);
    }

    public static String alphaToNum(String currency) {
        if (currency == null) {
            return null;
        }
        currency = currency.trim().toUpperCase();
        if (currency.length() == 3 && StringUtils.isAlpha(currency)) {
            return curC.get(currency);
        }
        return currency;
    }

    /**
     * 分转元
     * @param amount
     * @param currency
     * @return
     */
    public static Double parseAmount(double amount, String currency) {
        if (curs.get(currency) == null) {
            return amount;
        }
        if (0 == curs.get(currency)) {
            return amount;
        }
        if (2 == curs.get(currency)) {
            BigDecimal bd = new BigDecimal(amount);
            return bd.setScale(2, RoundingMode.HALF_EVEN)
                    .divide(new BigDecimal(100).setScale(0, RoundingMode.HALF_EVEN), RoundingMode.HALF_EVEN).doubleValue();
        }
        if (3 == curs.get(currency)) {
            BigDecimal bd = new BigDecimal(amount);
            return bd.setScale(2, RoundingMode.HALF_EVEN)
                    .divide(new BigDecimal(1000).setScale(0, RoundingMode.HALF_EVEN), RoundingMode.HALF_EVEN).doubleValue();
        }
        return amount;
    }

    /**
     * 元转分
     * @param amount
     * @param currency
     * @return
     */
    public static Double double2Integer(String amount, String currency) {
        Double d = Double.parseDouble(amount);
        if (curs.get(currency) == null) {
            return d;
        }
        if (0 == curs.get(currency)) {
            return d;
        }
        if (2 == curs.get(currency)) {
            return DoubleUtils.mul(d, 100D);
        }
        if (3 == curs.get(currency)) {
            return DoubleUtils.mul(d, 1000D);
        }
        return d;
    }

    public static String format(String amount, String currency) {

        if (curs.get(currency) == null) {
            return amount;
        }
        if (0 == curs.get(currency)) {
            return amount;
        }

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumIntegerDigits(12);
        //nf.setMinimumIntegerDigits(12);
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        nf.setRoundingMode(RoundingMode.HALF_EVEN);

        if (2 == curs.get(currency)) {
            Double d = DoubleUtils.mul(Double.parseDouble(amount), 100D);
            return nf.format(d);
        }
        if (3 == curs.get(currency)) {
            Double d = DoubleUtils.mul(Double.parseDouble(amount), 1000D);
            return nf.format(d);
        }
        return amount;
    }
}
