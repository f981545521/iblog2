package cn.acyou.iblog.aop;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-14 下午 04:36]
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return CustomerContextHolder.getCustomerType();
    }
}
