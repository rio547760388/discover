package com.ronhan.iso8583;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author ﻿bing.shen@onerway.com
 * @date 2019/3/13
 * 说明：
 */
public class DoubleUtils {

    /**
     * 对 double 数据进行取精度，四舍五入
     *
     * @param value double数据
     * @param scale 精度位数(保留的小数位数)
     * @return 精度计算后的数据
     */
    public static double round(double value, int scale) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 对 double 数据进行取精度
     *
     * @param value        double数据
     * @param scale        精度位数(保留的小数位数)
     * @param roundingMode 精度取值方式
     * @return 精度计算后的数据
     * @see RoundingMode
     */
    public static double round(double value, int scale, RoundingMode roundingMode) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(scale, roundingMode);
        return bd.doubleValue();
    }

    /**
     * double 相加
     *
     * @param d1
     * @param dn n 个被加数
     * @return
     */
    public static Double sum(Double d1, Double... dn) {
        if (d1 == null) d1 = 0.0;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        for (Double d2 : dn) {
            if (d2 == null || d2.equals(0d)) continue;
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            bd1 = bd1.add(bd2);
        }
        return bd1.doubleValue();
    }

    /**
     * double 相加
     *
     * @param d1
     * @param dn n 个被加数
     * @return
     */
    public static Double sumRound(Double d1, Double... dn) {
        return round(sum(d1, dn), 2);
    }

    /**
     * double 减法
     *
     * @param d1
     * @param dn n 个被减数
     * @return
     */
    public static Double sub(Double d1, Double... dn) {
        if (d1 == null) d1 = 0.0;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        for (Double d2 : dn) {
            if (d2 == null || d2.equals(0d)) continue;
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            bd1 = bd1.subtract(bd2);
        }
        return bd1.doubleValue();
    }

    /**
     * double 减法
     *
     * @param d1
     * @param dn n 个被减数
     * @return
     */
    public static Double subRound(Double d1, Double... dn) {
        return round(sub(d1, dn), 2);
    }

    /**
     * double 连乘
     *
     * @param d1
     * @param dn n 个被乘数
     * @return
     */
    public static Double mul(Double d1, Double... dn) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        for (Double d2 : dn) {
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            bd1 = bd1.multiply(bd2);
        }
        return bd1.doubleValue();
    }

    /**
     * double 连乘并保留2位小数
     *
     * @param d1
     * @param dn n 个被乘数
     * @return
     */
    public static Double mulRound(Double d1, Double... dn) {
        return round(mul(d1, dn), 2);
    }

    /**
     * double 除法
     *
     * @param d1
     * @param d2
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static Double div(Double d1, Double d2, int scale) {
        if (d2 == null || d2.equals(0D))
            return 0D;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        return bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * double 连除
     *
     * @param d1
     * @param dn
     * @param scale 四舍五入 小数点位数
     * @return
     */
    public static Double div(Double d1, int scale, Double... dn) {
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        for (Double d2 : dn) {
            BigDecimal bd2 = new BigDecimal(Double.toString(d2));
            bd1 = bd1.divide(bd2, scale, BigDecimal.ROUND_HALF_UP);
        }
        return bd1.doubleValue();
    }

}
