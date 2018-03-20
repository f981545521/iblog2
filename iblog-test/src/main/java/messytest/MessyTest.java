package messytest;

import cn.acyou.iblog.entity.People;
import com.google.common.primitives.Doubles;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-26 11:57
 **/
public class MessyTest {
    public static void main(String[] args) {
        sayHello();
        String hello = sayHello();
        System.out.println(hello);
    }

    public static String sayHello(){
        return "Hello";
    }

    @Test
    public void test1(){
        List<String> list = new ArrayList<>();
        Collections.synchronizedList(list);
    }

    @Test
    public void test2(){
        System.out.println(2 << 2);//2*2^3
        System.out.println(1 << 30);//2^30
    }

    @Test
    public void test3() {
        int val = 2;
        for (int i = 1; i < 30; i++) {
            val = val * 2;
        }
        System.out.println(val);
        System.out.println(1 << 30);//2^30
    }

    @Test
    public void testStringStartWith(){
        String billType = "GD20180302001";
        System.out.println( billType.startsWith("GD"));
    }

    @Test
    public void test12(){
        Double d1 = 25D;
        Integer i1 = 14;
        Double d2 = 30D;
        Integer i2 = 6;
        System.out.println((d1*i1+d2*i2)/(i1+i2));
    }

    @Test
    public void test13(){
        short s = 123;

    }

    @Test
    public void test14(){
        //三元运算符
        System.out.println(3 < 1?2:(4<2?3:6));
    }

    @Test
    public void test15(){
        String number = "7L";
        System.out.println(Doubles.tryParse(number));
        NumberUtils.isDigits(number);
        System.out.println(NumberUtils.isNumber(number));
        System.out.println(number.endsWith("L"));
        String result = "";
        try {
            Double d = Double.parseDouble(number);
            System.out.println(d);
        }catch (Exception e){
            result = number;
        }
        System.out.println(result);
    }

    @Test
    public void test16(){
        People people = new People();
        people.setId(2);
        people.setName("硝酸钠");
        System.out.println(people);
    }
}
