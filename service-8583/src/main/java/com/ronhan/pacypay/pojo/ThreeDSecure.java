package com.ronhan.pacypay.pojo;

import lombok.Data;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

/**
 * @author Mloong
 * @version 0.0.1
 * <p></p>
 * @since 2021/8/16
 **/
@Data
public class ThreeDSecure {

    private String type;

    private String eci;

    private String cavv;

    private String ipAddress;

    private String formattedDe122;

    public String format3D() {
        /*StringBuilder sb = new StringBuilder();
        String decodeCavv = Hex.encodeHexString(Base64.getDecoder().decode(cavv));
        if (StringUtils.isEmpty(type)) {
            if ("4".equals(eci)) {
                type = "3";
            } else if ("5".equals(eci) || "6".equals(eci) || "7".equals(eci) || "8".equals(eci)) {
                type = "2";
            } else if ("0".equals(eci) || "9".equals(eci)) {
                type = "2";
            }
        }
        sb.append(type);
        sb.append(Integer.valueOf(eci));
        sb.append(decodeCavv, 0, 2);
        sb.append(decodeCavv, 2, 4);
        sb.append(decodeCavv, 4, 6);
        sb.append(decodeCavv, 6, 10);
        sb.append(decodeCavv, 10, 14);
        sb.append(decodeCavv, 14, 30);
        if (ipAddress == null) {
            sb.append("00");
            sb.append("00000000");
        } else {
            sb.append("10");
            String[] ipArr = ipAddress.split("\\.");
            String part1 = String.format("%02x", Integer.parseInt(ipArr[0]));
            String part2 = String.format("%02x", Integer.parseInt(ipArr[1]));
            String part3 = String.format("%02x", Integer.parseInt(ipArr[2]));
            String part4 = String.format("%02x", Integer.parseInt(ipArr[3]));
            sb.append(part1).append(part2).append(part3).append(part4);
        }

        return sb.toString();*/
        return formattedDe122;
    }
}
