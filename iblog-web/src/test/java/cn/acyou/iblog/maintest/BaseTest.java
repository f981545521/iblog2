package cn.acyou.iblog.maintest;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author youfang
 * @create 2018-02-08 22:45
 */
public class BaseTest {

    protected ClassPathXmlApplicationContext ctx;

    protected final Logger log = LoggerFactory.getLogger(getClass());

    public BaseTest() {
        super();
    }

    @Before
    public void init() {
        ctx=new ClassPathXmlApplicationContext
                ("spring/spring-mvc.xml","spring/spring-mybatis.xml","spring/spring-service.xml");

    }

    @After
    public void destroy() {
        ctx.close();
    }

}