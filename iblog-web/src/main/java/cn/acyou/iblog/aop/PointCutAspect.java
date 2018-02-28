package cn.acyou.iblog.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author youfang
 * @date 2018-02-26 22:37
 */
@Component
@Aspect
public class PointCutAspect {

    //@Before("bean(*Service)")
    @Before("within(cn.acyou.iblog.*.impl.*ServiceImpl)")
    public void test(){
        System.out.println("切入点测试。");
    }


}