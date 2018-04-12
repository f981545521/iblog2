package cn.acyou.iblog.constant;

/**
 * @author youfang
 * @date 2018-02-09 11:08
 **/
public class AppConstant {

    public static final String SHORT_DATE_PATTERN = "yyyyMMdd";
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    public static final String SPECIFIC_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    //默认超级管理员ID
    public static final Integer DEFAULT_USER = 1;
    //默认本地IP
    public static final String DEFAULT_LOCALHOST_IP = "0:0:0:0:0:0:0:1";
    //南京电信IP
    public static final String NANJING_DIANXIN_IP = "114.222.237.206";
    public static final String NANJING = "南京";


    public static final String SUCCESS = "SUCCESS";
    public static final String FAILD = "FAILD";

    public static final double CELL_WIDTH = 30;

    public static final int FALSE = 505;
    /**
     * optimist locker status
     * Modified By Another User
     */
    public static final int MODIFIED_BY_ANOTHER_USER = 502;
    public static final String MODIFIED_BY_ANOTHER_USER_MSG = "页面内容过期了,请刷新页面后再继续操作!";
    /**
     * optimist locker status
     * Removed By Another User
     */
    public static final int REMOVED_BY_ANOTHER_USER = 503;
    public static final String REMOVED_BY_ANOTHER_USER_MSG = "页面数据已经被删除,请稍后刷新再试!";

    public static final String DEFAULT_EXCEPTION_MSG = "系统异常，请稍后再试！";

}
