package cn.acyou.iblog.utility;

import cn.acyou.iblog.constant.AppConstant;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.Date;

/**
 * @author youfang
 * @date 2018-02-09 11:23
 **/
public final class DateUtil {
    private DateUtil() {
    }

    public static String getDateFormat(Date date) {
        return getDateFormat(date, AppConstant.SPECIFIC_DATE_FORMAT_PATTERN);
    }

    public static String getDateShortFormat(Date date) {
        return getDateFormat(date, AppConstant.SHORT_DATE_PATTERN);
    }

    public static String getDateFormat(Date date, String format) {
        return new DateTime(date).toString(format);
    }

    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, AppConstant.DEFAULT_DATE_FORMAT_PATTERN);
    }

    public static Date parseDate(String dateStr, String format) {
        return DateTimeFormat.forPattern(format).parseDateTime(format).toDate();
    }

    public static Date addDay(Date date, int day) {
        return new DateTime(date).plusDays(day).toDate();
    }

    public static Date addMonth(Date date, int month) {
        return new DateTime(date).plusMonths(month).toDate();
    }

    public static Date addHour(Date date, int hour) {
        return new DateTime(date).plusHours(hour).toDate();
    }

}