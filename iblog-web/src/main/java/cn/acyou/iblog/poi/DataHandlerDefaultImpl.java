package cn.acyou.iblog.poi;

import cn.acyou.iblog.constant.AppConstant;
import com.google.common.collect.Lists;
import org.jeecgframework.poi.handler.impl.ExcelDataHandlerDefaultImpl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.List;

/**
 * @author youfang
 * @date 2018-03-12 15:17
 **/
public class DataHandlerDefaultImpl<T> extends ExcelDataHandlerDefaultImpl<T> {
    private static final List<String> DATE_PATTERN_LIST
            = Lists.newArrayList(AppConstant.DEFAULT_DATE_FORMAT_PATTERN, "yyyy/MM/dd", "yyyy年MM月dd日");
    private static final List<DateTimeFormatter> DATE_TIME_FORMATTERS = Lists.newArrayListWithExpectedSize(DATE_PATTERN_LIST.size());

    static {
        for (String datePatten : DATE_PATTERN_LIST) {
            DATE_TIME_FORMATTERS.add(DateTimeFormat.forPattern(datePatten));
        }
    }

    public Date parseDate(String val) {
        if (val == null) {
            return null;
        }
        for (DateTimeFormatter formatter : DATE_TIME_FORMATTERS) {
            try {
                DateTime dateTime = formatter.parseDateTime(val);
                return dateTime.toDate();
            } catch (IllegalArgumentException ignore) {

            }
        }
        return null;
    }
}