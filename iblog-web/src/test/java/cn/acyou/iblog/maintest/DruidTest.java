package cn.acyou.iblog.maintest;

import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring Test
 * @author youfang
 * @date 2018-02-09 10:11
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-mvc.xml","classpath:spring/spring-mybatis.xml","classpath:spring/spring-service.xml"})
public class DruidTest extends AbstractJUnit4SpringContextTests{
    @Test
    public void test1(){
        DruidDataSource dataSource = applicationContext.getBean("dataSource", DruidDataSource.class);
        logger.info("Spring Test ...");
        System.out.println(dataSource);
    }
}
