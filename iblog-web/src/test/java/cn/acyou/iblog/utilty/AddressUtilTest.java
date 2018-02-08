package cn.acyou.iblog.utilty;

import java.util.List;

import cn.acyou.iblog.utility.AddressUtil;
import cn.acyou.iblog.utility.WeatherUtil;
import org.junit.Test;

public class AddressUtilTest {
    @Test
    public void test1(){
        AddressUtil addressUtils = new AddressUtil();
        String ip = "114.222.237.206";
        List<String> list = null;
        try {
            list = addressUtils.getAddress("ip=" + ip, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        for(String s:list){
            System.out.println(s);
        }
    }


    @Test
    public void test2(){
        WeatherUtil weath = new WeatherUtil();
        // 查看城市：南京市
        String weather = weath.getWeather("南京");
        System.out.println(weather);
		String len[] = weather.split("#");
		for (int i = 0; i < len.length - 1; i++) {
			System.out.println(len[i]);
		}
    }
}