package cn.acyou.iblog.maintest;

import cn.acyou.iblog.service.ActiveCodeService;
import cn.acyou.iblog.utility.AddressUtil;
import cn.acyou.iblog.utility.WeatherUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.util.IPAddress;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * @author youfang
 * @create 2018-02-08 22:46
 */
public class testDruid extends BaseTest{

    @Test
    public void test1() {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/spring-mvc.xml", "spring/spring-mybatis.xml", "spring/spring-service.xml");
        ActiveCodeService service = ctx.getBean("activeCodeService", ActiveCodeService.class);
/*        String mess = service.saveActiveCode("youfang@acyou.cn");
        System.out.println(mess);*/
    }

    @Test
    public void test2() {
        DruidDataSource dataSource = applicationContext.getBean("dataSource", DruidDataSource.class);
        System.out.println(dataSource);
    }

    @Test
    public void test3(){
        WeatherUtil weatherUtil = applicationContext.getBean("weatherUtil",WeatherUtil.class);
        String weather = weatherUtil.getWeather("南京");
        System.out.println(weather);
    }
    @Test
    public void test4(){
        AddressUtil addressUtil = applicationContext.getBean("addressUtil",AddressUtil.class);
        String ip = "114.222.237.206";
        List<String> list = null;
        try {
            list = addressUtil.getAddress("ip=" + ip, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(list);

    }
}
