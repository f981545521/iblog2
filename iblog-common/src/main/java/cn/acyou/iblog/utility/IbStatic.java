package cn.acyou.iblog.utility;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地线程
 * 利用ThreadLocal解决线程安全问题
 * @author youfang
 * @date 2018-03-03 13:03
 **/
public class IbStatic {

    public static final String NORMAL = "dataSource";

    public static final String READONLY = "dataSource2";

    private static final ThreadLocal<String> IP_TL = new ThreadLocal<>();
    private static final ThreadLocal<Integer> USER_TL = new ThreadLocal<>();
    private static final ThreadLocal<String> ACTION_TL = new ThreadLocal<>();
    private static final List<ThreadLocal> THREAD_LOCAL_LIST = new ArrayList<>();
    //数据源
    private static final ThreadLocal<String> DATA_SOURCE = new ThreadLocal<String>();

    static {
        THREAD_LOCAL_LIST.add(IP_TL);
        THREAD_LOCAL_LIST.add(USER_TL);
        THREAD_LOCAL_LIST.add(ACTION_TL);
        THREAD_LOCAL_LIST.add(DATA_SOURCE);
    }

    public static String getIp() {
        return IP_TL.get();
    }

    public static void setIp(String ip) {
        IP_TL.set(ip);
    }

    public static void clearIp() {
        IP_TL.remove();
    }

    public static Integer getUser() {
        return USER_TL.get();
    }

    public static void setUser(Integer user) {
        USER_TL.set(user);
    }

    public static void clearUser() {
        USER_TL.remove();
    }

    public static String getAction() {
        return ACTION_TL.get();
    }

    public static void setAction(String action) {
        ACTION_TL.set(action);
    }

    public static void clearAction() {
        ACTION_TL.remove();
    }

    public static String getRemoteIp(HttpServletRequest request) {

        String remoteAddr = request.getRemoteAddr();
        String x;
        if ((x = request.getHeader("X-Forwarded-For")) != null) {
            remoteAddr = x;
            int idx = remoteAddr.indexOf(',');
            if (idx > -1) {
                remoteAddr = remoteAddr.substring(0, idx);
            }
        }
        return remoteAddr;
    }

    public static void clearThreadLocal() {
        for (ThreadLocal tl : THREAD_LOCAL_LIST) {
            if (tl != null) {
                tl.remove();
            }
        }
    }


    public static void setDataSource(String dataSource) {
        DATA_SOURCE.set(dataSource);
    }

    public static String getDataSource() {
        return DATA_SOURCE.get();
    }

    public static void clearDataSource() {
        DATA_SOURCE.remove();
    }
}
