package cn.acyou.iblog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Service的Aspect切面用来打印请求参数/返回结果
 * @author youfang
 * @version [1.0.0, 2018-06-25 下午 04:55]
 **/
@Component
@Aspect
public class ServiceLogAspect {

    @Around("within(cn.acyou.iblog.*.impl.*ServiceImpl)")
    public Object test5(ProceedingJoinPoint jp) throws Throwable {
        //业务方法前
        Object value = jp.proceed();//调用业务方法
        //业务方法后
        System.out.println("@Around环绕通知" + value);
        return value;
    }

}
