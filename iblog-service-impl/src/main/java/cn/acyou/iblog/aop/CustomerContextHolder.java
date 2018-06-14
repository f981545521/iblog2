package cn.acyou.iblog.aop;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-14 下午 04:37]
 **/
public class CustomerContextHolder {

    public static final String DATA_SOURCE_A = "dataSource";

    public static final String DATA_SOURCE_B = "dataSource2";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    public static String getCustomerType() {
        return contextHolder.get();
    }

    public static void clearCustomerType() {
        contextHolder.remove();
    }

}