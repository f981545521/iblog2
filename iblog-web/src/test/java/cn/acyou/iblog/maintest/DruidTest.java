package cn.acyou.iblog.maintest;

import cn.acyou.iblog.redis.RedisUtil;
import cn.acyou.iblog.utility.FtpUtil;
import com.alibaba.druid.pool.DruidDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.File;
import java.io.FileInputStream;

/**
 * Spring Test
 * @author youfang
 * @date 2018-02-09 10:11
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring/spring-mvc.xml","classpath:spring/spring-mybatis.xml","classpath:spring/spring-service.xml"})
public class DruidTest extends AbstractJUnit4SpringContextTests{
    @Test
    public void test1(){
        DruidDataSource dataSource = applicationContext.getBean("dataSource", DruidDataSource.class);
        logger.info("Spring Test ...");
        System.out.println(dataSource);
    }
    @Test
    public void test2() throws Exception{
        FtpUtil ftpUtil = applicationContext.getBean("ftpUtil", FtpUtil.class);
        FileInputStream in = new FileInputStream(new File("D:\\tmp\\400.jpg"));
        ftpUtil.uploadFile("1234562.jpg",in);
    }
    @Test
    public void test3() throws Exception{
        RedisUtil redisUtil = applicationContext.getBean("redisUtil", RedisUtil.class);
        System.out.println(redisUtil.getValue("a"));
    }

}
