package cn.acyou.iblog.aop;

import cn.acyou.iblog.annotation.DataSource;
import cn.acyou.iblog.annotation.DataSourceType;
import cn.acyou.iblog.utility.IbStatic;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 多数据源切换
 * @author youfang
 * @version [1.0.0, 2018-06-14 下午 05:40]
 **/
@Component
@Aspect
public class DataSourceChangeAspect {
    private static final Log logger = LogFactory.getLog(DataSourceChangeAspect.class);

    @Around("execution(public * cn.acyou..iblog.service..*.*(..)) && @annotation(cn.acyou.iblog.annotation.DataSource) && @annotation(dataSource)")
    public Object setDataSource(ProceedingJoinPoint joinPoint, DataSource dataSource) throws Throwable {
        DataSourceType dataSourceType = dataSource.value();
        try {
            IbStatic.setDataSource(dataSourceType.getValue());
            if (logger.isDebugEnabled()) {
                logger.debug("DataSourceType[" + dataSourceType.getValue() + "] set.");
            }
            return joinPoint.proceed();
        } finally {
            IbStatic.clearDataSource();
            if (logger.isDebugEnabled()) {
                logger.debug("DataSourceType[" + dataSourceType.getValue() + "] remove.");
            }
        }

    }

}
