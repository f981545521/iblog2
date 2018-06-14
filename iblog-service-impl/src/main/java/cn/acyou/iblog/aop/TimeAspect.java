package cn.acyou.iblog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @date 2018-02-26 22:20
 */
@Component
@Aspect
public class TimeAspect {
    @Around("bean(*Service)")
    public Object test(ProceedingJoinPoint jp) throws Throwable {

        long t1 = System.currentTimeMillis();
        Object val = jp.proceed();//目标业务方法
        long t2 = System.currentTimeMillis();
        long t = t2 - t1;
        //JoinPoint对象可以获取目标业务方法的详细信息：方法签名、调用参数等；
        Signature sign = jp.getSignature();//获取方法签名：方法名+参数列表
        //如：List cn.acyou.iblog.service.impl.SortServiceImpl.listSorts(Integer)用时：9 ms

        System.out.println(sign + "用时：" + t + " ms");
        return val;

    }
}
