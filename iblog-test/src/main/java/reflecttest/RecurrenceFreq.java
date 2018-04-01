package reflecttest;

import com.sun.org.glassfish.gmbal.Description;

/**
 * @author youfang
 * @date 2018-04-01 上午 10:54
 **/
public enum RecurrenceFreq {

    @Description(value = "无")
    NONE,
    @Description(value = "秒")
    SECONDLY,
    @Description(value = "分")
    MINUTELY,
    @Description(value = "时")
    HOURLY,
    @Description(value = "天")
    DAILY

}
