package cn.acyou.iblog.maintest;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author youfang
 * @date 2018-02-08 22:45
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:spring/spring-mvc.xml","classpath:spring/spring-mybatis.xml","classpath:spring/spring-service.xml"})
public class BaseTest  extends AbstractTransactionalJUnit4SpringContextTests {

}