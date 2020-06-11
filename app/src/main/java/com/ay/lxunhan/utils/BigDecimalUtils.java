package com.ay.lxunhan.utils;

import android.text.TextUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BigDecimalUtils {


    /**isNumeric
     * 根据小数位数获取0.01  0.001  0.001等
     */
    public static String getPointNumber(int point) {
        String pointDecimalStr = new BigDecimal(10).pow(point).stripTrailingZeros().toPlainString();
        return divideToString("1", pointDecimalStr);
    }

    public static float formatToFloat(String v) {
        if (TextUtils.isEmpty(v)) {
            return BigDecimal.ZERO.floatValue();
        }
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    public static long formatToLong(String v) {
        if (TextUtils.isEmpty(v)) {
            return BigDecimal.ZERO.longValue();
        }
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }


    public static String formatToString(String v) {
        if (TextUtils.isEmpty(v)) {
            return BigDecimal.ZERO.toPlainString();
        }
        return new BigDecimal(v).stripTrailingZeros().toPlainString();
    }

    public static String formatRoundHalfUp(String v, int point) {
        if (TextUtils.isEmpty(v) || compare(v, "0") == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        }
        return new BigDecimal(v).setScale(point, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String formatRoundHalfUpConsZero(String v, int point) {
        if (TextUtils.isEmpty(v) || compare(v, "0") == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.HALF_UP).toPlainString();
        }
        return new BigDecimal(v).setScale(point, RoundingMode.HALF_UP).toPlainString();
    }

    public static String formatUp(String v, int point) {
        if (TextUtils.isEmpty(v) || compare(v, "0") == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.UP).stripTrailingZeros().toPlainString();
        }
        return new BigDecimal(v).setScale(point, RoundingMode.UP).stripTrailingZeros().toPlainString();
    }

    public static String formatRoundUpConsZero(String v, int point) {
        if (TextUtils.isEmpty(v) || compare(v, "0") == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.HALF_UP).toPlainString();
        }
        return new BigDecimal(v).setScale(point, RoundingMode.HALF_UP).toPlainString();
    }

    public static String formatDown(String v, int point) {
        if (TextUtils.isEmpty(v) || compare(v, "0") == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        }
        return new BigDecimal(v).setScale(point, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }


    public static String formatDownConsZero(String v, int point) {
        if (TextUtils.isEmpty(v) || compare(v, "0") == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.DOWN).toPlainString();
        }
        return new BigDecimal(v).setScale(point, RoundingMode.DOWN).toPlainString();
    }

    public static String add(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.add(n2).stripTrailingZeros().toPlainString();
    }

    public static String add(List<String> datas) {
        BigDecimal n1 = BigDecimal.ZERO;
        if (datas.size() == 0) {
            return "0.00000000";
        } else {
            for (String data : datas) {
                if (Utils.isNumeric(data)) {
                    n1 = n1.add(new BigDecimal(data));
                }
            }
            return n1.toPlainString();
        }
    }


    public static String subtract(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.subtract(n2).stripTrailingZeros().toPlainString();
    }

    public static String subtractZero(String v1, String v2) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.subtract(n2).toPlainString();
    }

    public static float subtractToFloat(String v1, float v2) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.subtract(n2).stripTrailingZeros().floatValue();
    }

    public static int multiplyToInt(String v1, int v2) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.stripTrailingZeros().multiply(n2).intValue();
    }

    public static String multiplyToString(String v1, String v2) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        if (n1.compareTo(BigDecimal.ZERO) == 0 || n2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.toPlainString();
        }
        return n1.multiply(n2).stripTrailingZeros().toPlainString();
    }


    public static String multiplyToStringDown(String v1, String v2, int point) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        if (n1.compareTo(BigDecimal.ZERO) == 0 || n2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.DOWN).toPlainString();
        }
        return n1.multiply(n2).setScale(point, RoundingMode.DOWN).toPlainString();
    }


    public static String multiplyToStringFormatPointDown(String v1, String v2, int point) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        if (n1.compareTo(BigDecimal.ZERO) == 0 || n2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
        }
        return n1.multiply(n2).setScale(point, RoundingMode.DOWN).stripTrailingZeros().toPlainString();
    }

    public static String multiplyToStringFormatPointDownConitsZero(String v1, String v2, int point) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        if (n1.compareTo(BigDecimal.ZERO) == 0 || n2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.DOWN).toPlainString();
        }
        return n1.multiply(n2).setScale(point, RoundingMode.DOWN).toPlainString();
    }

    public static String multiplyToStringFormatPointHalfUpConitsZero(String v1, String v2, int point) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        if (n1.compareTo(BigDecimal.ZERO) == 0 || n2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.HALF_UP).toPlainString();
        }
        return n1.multiply(n2).setScale(point, RoundingMode.HALF_UP).toPlainString();
    }

    public static String multiplyToStringFormatPointHalfUp(String v1, String v2, int point) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        if (n1.compareTo(BigDecimal.ZERO) == 0 || n2.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO.setScale(point, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        }
        return n1.multiply(n2).setScale(point, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String divideToString(String v1, String v2) {

        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0 || TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            return BigDecimal.ZERO.toPlainString();
        }

        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.divide(n2, 8, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }

    public static String divideToStringHalfUp(String v1, String v2, int point) {
        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0 || TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            return BigDecimal.ZERO.toPlainString();
        }

        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.divide(n2, point, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
    }


    public static String divideToStringHalfUpConsZero(float v1, String v2, int point) {
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            return BigDecimal.ZERO.toPlainString();
        }

        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.divide(n2, point, RoundingMode.HALF_UP).toPlainString();
    }

    public static String divideToStringDownConsZero(float v1, String v2, int point) {
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            return BigDecimal.ZERO.toPlainString();
        }

        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.divide(n2, point, RoundingMode.DOWN).toPlainString();
    }

    public static float divideToFloatUp(int v1, int v2) {
        if (v2 == 0 || v1 == 0) {
            return BigDecimal.ZERO.intValue();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.divide(n2, 8, RoundingMode.HALF_UP).stripTrailingZeros().floatValue();
    }

    public static int compare(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1)) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2)) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        BigDecimal n1 = new BigDecimal(v1);
        BigDecimal n2 = new BigDecimal(v2);
        return n1.compareTo(n2);
    }

    public static boolean compareIsBig(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        if (compare(v1, v2) >= 0) {
            return true;
        }
        return false;
    }
    public static boolean compareNotBig(String v1, String v2) {
        if (TextUtils.isEmpty(v1) || !Utils.isNumeric(v1) || compare(v1, "0") == 0) {
            v1 = BigDecimal.ZERO.toPlainString();
        }
        if (TextUtils.isEmpty(v2) || !Utils.isNumeric(v2) || compare(v2, "0") == 0) {
            v2 = BigDecimal.ZERO.toPlainString();
        }
        if (compare(v1, v2) <= 0) {
            return true;
        }
        return false;
    }

}
