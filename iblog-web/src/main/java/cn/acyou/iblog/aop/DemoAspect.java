package cn.acyou.iblog.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 创建一个切面组件，就是一个普通的JavaBean
 * 使用@Component必须要加入组件扫描
 */
@Component
@Aspect
public class DemoAspect {

    /**
     * 声明test方法将在userService的全部方法之前运行.
     */
    @Before("bean(sortService)")
    public void test() {
        System.out.println("@Before...");
    }

    @After("bean(sortService)")
    public void test2() {
        System.out.println("@After...");
    }

    @AfterReturning("bean(sortService)")
    public void test3() {
        System.out.println("@AfterReturning...");
    }

    @AfterThrowing("bean(sortService)")
    public void test4() {
        System.out.println("@AfterThrowing...");
    }

    /**
     * 环绕通知 方法：
     * 1. 必须有返回值;
     * 2. 必须有参数 ProceedingJoinPoint;
     * 3. 必须抛出异常;
     * 4. 需要在方法中调用 Object val=jp.proceed();
     * 5. 返回业务方法的返回值
     */
    @Around("bean(sortService)")
    public Object test5(ProceedingJoinPoint jp) throws Throwable {
        //业务方法前
        Object value = jp.proceed();//调用业务方法
        //业务方法后
        System.out.println("@Around环绕通知" + value);
        return value;
    }
}
