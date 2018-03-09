package cn.acyou.iblog.utility;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数据处理工具类。
 */
public class MathUtil {

    private static final Log logger = LogFactory.getLog(MathUtil.class);
    private static final double ZERO_GAP = Math.pow(10, -6);

    /**
     * 两Double型数据相加
     *
     * @param value
     * @param addValue
     * @return
     */
    public static Double addDoubles(Double value, Double addValue) {
        BigDecimal sum = BigDecimal.ZERO; // new BigDecimal(0)
        if (value != null) {
            sum = new BigDecimal(String.valueOf(value));
        }
        if (addValue != null) {
            sum = sum.add(new BigDecimal(String.valueOf(addValue)));
        }
        return sum.doubleValue();
    }

    /**
     * 两Double型数据相减
     *
     * @param value
     * @param subValue
     * @return
     */
    public static Double subDoubles(Double value, Double subValue) {
        if (subValue == null) {
            subValue = 0D;
        }
        return addDoubles(value, subValue * -1);
    }

    /**
     * 两数相乘
     *
     * @param value1
     * @param value2
     * @return
     */
    public static Double multiply(Object value1, Object value2) {
        if (value1 == null || value2 == null) {
            return 0d;
        }
        BigDecimal val1 = new BigDecimal(value1.toString());
        BigDecimal val2 = new BigDecimal(value2.toString());

        return val1.multiply(val2).doubleValue();
    }

    /**
     * 两数相除
     *
     * @param value
     * @param divisor
     * @param len     保留的小数位
     * @return
     */
    public static Double divide(Object value, Object divisor, int len) {
        if (value == null || divisor == null) {
            return 0d;
        }

        BigDecimal val1 = new BigDecimal(value.toString());
        BigDecimal val2 = new BigDecimal(divisor.toString());

        if (val2.doubleValue() == 0D) {
            logger.error("divisor is null or zero");
            throw new IllegalArgumentException("divisor is null or zero");
        }

        return val1.divide(val2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Double divide(Object value, Object divisor) {
        return divide(value, divisor, 4);
    }

    /**
     * 根据factor因子结合两个Double数字，factor为 1 则两数相加，为 -1 则两数相减
     *
     * @param first
     * @param second
     * @param factor 1 或 -1；
     * @return
     */
    public static Double mix(Double first, Double second, int factor) {
        if (Math.abs(factor) != 1) {
            throw new IllegalArgumentException("错误的因子！factor只能为1或-1");
        }

        first = first == null ? 0D : first;
        second = second == null ? 0D : second;
        // return first + second * factor;
        return addDoubles(first, second * factor);
    }

    /**
     * Double型加法运算。
     *
     * @param first
     * @param second
     * @return
     */
    public static Double add(Double first, Double second) {
        return addDoubles(first, second);
    }

    public static Double add(Double first, Double second, Double third) {
        return add(add(first, second), third);
    }

    /**
     * Double型减法运算。
     *
     * @param first
     * @param second
     * @return
     */
    public static Double sub(Double first, Double second) {
        return subDoubles(first, second);
    }

    public static Double sub(Double first, Double second, Double third) {
        return sub(sub(first, second), third);
    }

    public static Double reverse(Double value) {
        if (value == null) {
            return 0d;
        }
        return -value;
    }

    public static Double abs(Double value) {
        if (value == null) {
            return 0D;
        }
        return Math.abs(value);
    }

    /**
     * 这里是五舍六入的，所以如果想要按四舍五入规则round时请谨慎调用
     *
     * @param size   保留小数位数
     * @param number
     * @return
     */
    public static Double roundUp(int size, Double number) {
        StringBuilder formatString = new StringBuilder("0");
        if (size > 0) {
            formatString.append(".");
        }
        String numberStr = number.toString();
        int indexDot = numberStr.indexOf(".");
        int numberDot = numberStr.substring(indexDot).length() - 1;
        size = size > numberDot ? numberDot : size;
        for (int i = 0; i < size; i++) {
            formatString.append("#");
        }
        DecimalFormat df = new DecimalFormat(formatString.toString());
        return Double.valueOf(df.format(number));
    }

    /**
     * 这里是五舍六入的，所以如果想要按四舍五入规则round时请谨慎调用
     *
     * @param value
     * @param size  保留小数位数
     * @return
     */
    public static Double round(Double value, int size) {
        String pattern = "#0.0";
        if (size > 1) {
            for (int i = 0; i < size - 1; i++) {
                pattern += "0";
            }
        }

        DecimalFormat df = new DecimalFormat(pattern);
        return Double.valueOf(df.format(value));
    }

    /**
     * 小数部分小于0.5则取 ceil + 0.5，大于0.5则取floor，否则取原数 0.5进位
     *
     * @param value
     * @return
     */
    public static Double halfRound(Double value) {
        double res = 0;
        long r = Math.round(value);
        double f = Math.floor(value);
        if (r == f || f + 0.5 == value) {
            if (r == value) {
                res = value;
            } else {
                res = f + 0.5d;
            }
        } else {
            res = Math.ceil(value);
        }
        return res;
    }

    /**
     * 浮点型数据是否等于0 空等于o
     *
     * @param d
     * @return
     */
    public static boolean isEqualZero(Double d) {
        return d == null || compareDouble(d, 0.0d) == 0;
    }

    /**
     * 判断两个Double对象值是否相等
     *
     * @param src
     * @param des
     * @return
     */
    public static boolean isEqualValue(Double src, Double des) {
        double srcDoubleValue = src == null ? 0 : src;
        double desDoubleValue = des == null ? 0 : des;

        return srcDoubleValue == desDoubleValue;
    }

    /**
     * 比较两个Double值的大小
     * @param src 源值
     * @param des 目标值
     * @return 大于零，则src > des，等于零，则两者相等，小于零，则src < des
     * @deprecated 以后都请使用compareDouble方法
     */
    @Deprecated
    public static int compareValue(Double src, Double des) {
        return compareDouble(src, des);
    }

    /**
     * @param src
     * @param des
     * @return
     */
    public static int compareValue(Long src, Long des) {
        long srcLongValue = src == null ? 0 : src;
        long desLongValue = des == null ? 0 : des;

        if (srcLongValue > desLongValue) {
            return 1;
        } else if (srcLongValue == desLongValue) {
            return 0;
        } else {
            return -1;
        }
    }

    public static boolean isAboveZero(Double d) {
        return MathUtil.compareDouble(d, 0.0D) > 0;
    }

    public static int compareDouble(Double d1, Double d2) {
        double f1 = d1 == null ? 0 : d1;
        double f2 = d2 == null ? 0 : d2;
        if (Math.abs(f1 - f2) < ZERO_GAP) {
            return 0;
        } else if (f1 > f2) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * 返回两个数中较大的一个数
     * @param d1
     * @param d2
     * @return
     */
    public static Double maxDouble(Double d1, Double d2) {
        if (compareDouble(d1, d2) > 0) {
            return  d1;
        } else {
            return d2;
        }
    }

    /**
     * 返回两个数中较小的一个数
     * @param d1
     * @param d2
     * @return
     */
    public static Double minDouble(Double d1, Double d2) {
        if (compareDouble(d1, d2) >= 0) {
            return  d2;
        } else {
            return d1;
        }
    }

    public static Double minAbsDouble(Double d1, Double d2) {
        if (compareDouble(MathUtil.abs(d1), MathUtil.abs(d2)) >= 0) {
            return  d2;
        } else {
            return d1;
        }
    }

    /**
     * 同BigDecimal的ROUND_HALF_UP（四舍五入）
     * 同前端的toFixed
     * @param number 需要转换的Double值
     * @param precision 精度
     * @return
     */
    public static Double roundHalfUp(Double number, int precision ) {
        BigDecimal bg = new BigDecimal(String.valueOf(number));
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 同BigDecimal的ROUND_CEILING（去尾舍入）
     * @param number 需要转换的Double值
     * @param precision 精度
     * @return
     */
    public static Double roundCeiling(Double number, int precision ) {
        BigDecimal bg = new BigDecimal(String.valueOf(number));
        return bg.setScale(precision, BigDecimal.ROUND_CEILING).doubleValue();
    }

    /**
     * 同BigDecimal的ROUND_FLOOR（去尾）
     * @param number 需要转换的Double值
     * @param precision 精度
     * @return
     */
    public static Double roundFloor(Double number, int precision ) {
        BigDecimal bg = new BigDecimal(String.valueOf(number));
        return bg.setScale(precision, BigDecimal.ROUND_FLOOR).doubleValue();
    }

    /**
     * 判断字符串为含前导零整数,0返回false
     */
    public static boolean isLeadingZeroInteger(String numStr) {
        if (StringUtils.isNotBlank(numStr) &&
                NumberUtils.isNumber(numStr) &&
                numStr.indexOf(".") == -1 &&
                numStr.length() > 1 &&
                numStr.charAt(0) == '0') {
            return true;
        }
        return false;
    }
}
