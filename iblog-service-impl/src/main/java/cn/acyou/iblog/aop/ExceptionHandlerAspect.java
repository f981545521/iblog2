package cn.acyou.iblog.aop;

import cn.acyou.iblog.exception.BusinessException;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import se.spagettikod.optimist.ModifiedByAnotherUserException;
import se.spagettikod.optimist.RemovedByAnotherUserException;

import java.lang.reflect.Method;

/**
 * @author youfang
 * @date 2018-04-12 下午 09:30
 **/
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Pointcut("execution(public *  cn.acyou.iblog.service..*(..))")
    public void aspect() {

    }

    @Around("aspect()")
    //我们的目的是不抛出异常，所有均包装成为可读异常
    public Object handleException(ProceedingJoinPoint pjp) throws Throwable {
        String rawThreadName = Thread.currentThread().getName();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        try {
            String[] split = rawThreadName.split("-");
            if (split.length > 1 && split[0].equals("http")) {
                Thread.currentThread().setName(method.getName() + "-" + split[split.length - 1]);
            }
            return pjp.proceed();
        } catch (Exception failure) {
            Throwable throwable = Throwables.getRootCause(failure);
            if (throwable instanceof BusinessException) {
                BusinessException bex = (BusinessException) throwable;
                logger.warn(bex.getMessage());
            } else if (throwable instanceof ModifiedByAnotherUserException ||
                    throwable instanceof RemovedByAnotherUserException ||
                    throwable instanceof IllegalArgumentException) {
                logger.warn(throwable.getMessage());
            } else {
                try {
                    StringBuilder stringBuilder = new StringBuilder()
                            .append(failure.getMessage())
                            .append("\r\nArgs:").append(JSON.toJSONString(pjp.getArgs()));
                    logger.error(stringBuilder.toString(), failure);
                } catch (Exception ex) {
                    logger.error("包装异常失败：" + ex.getMessage(), ex);
                }
            }
            throw failure;

        } finally {
            Thread.currentThread().setName(rawThreadName);
        }

    }


}