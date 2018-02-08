package cn.acyou.iblog.maintest;

import cn.acyou.iblog.service.ActiveCodeService;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author youfang
 * @create 2018-02-08 22:46
 */
public class testDruid extends BaseTest{

    @Test
    public void test1() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-mybatis.xml", "spring/spring-service.xml");
        ActiveCodeService service = ctx.getBean("activeCodeService", ActiveCodeService.class);
        String mess = service.sendActiveCode();
        System.out.println(mess);
    }

    @Test
    public void test2() {
        DruidDataSource dataSource = ctx.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
    }
}
