package cn.acyou.iblog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Service的Aspect切面用来打印请求参数/返回结果
 * @author youfang
 * @version [1.0.0, 2018-06-25 下午 04:55]
 **/
@Component
@Aspect
public class ServiceLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("within(cn.acyou.iblog.*.impl.*ServiceImpl)")
    public Object recordLog(ProceedingJoinPoint jp) throws Throwable {
        StringBuilder sb = new StringBuilder();
        for (Object arg : jp.getArgs()){
            String claz = arg.getClass().getSimpleName();
            String className = claz.substring(claz.lastIndexOf(".") + 1);
            sb.append(className).append(":(").append(arg.toString()).append(").");
        }
        logger.debug("[{}]|{}", jp.getSignature().toString(), sb);
        //业务方法前
        Object value = jp.proceed();//调用业务方法
        //业务方法后
        logger.debug("[{}]|{}", "return : ", value.toString());
        return value;
    }



}
