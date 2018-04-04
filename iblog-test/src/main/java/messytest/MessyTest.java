package messytest;

import cn.acyou.iblog.entity.People;
import cn.acyou.iblog.model.User;
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
        System.out.println(2 << 2);//2*2^2
        System.out.println(1 << 3);//1*2^3
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
        System.out.println(billType.startsWith("GD"));
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
        NumberUtils.createDouble(number);
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


    @Test
    public void test17(){
        String number = "7L";
        System.out.println(NumberUtils.isNumber(number));
        System.out.println(NumberUtils.createNumber(number).doubleValue());
    }

    @Test
    public void test18(){
        Double d1 = 3.45;
        Double d2 = 4.56;
        System.out.println();
    }

    @Test
    public void test19(){
        System.out.println((char)65);
        System.out.println((char)122);
    }

    /**
     * 数据类型转换:
     * 1. String无法强制转换为数值,字符,布尔型
     * 2. boolean 无法强制转换为数值,char,String
     * 3. 数值型 无法强制转换为String,boolean.
     */
    @Test
    public void test20(){
        User user = new User();
        user.setEmail("youfang@acyou.cn");
        System.out.println(String.valueOf(user));
        System.out.println(user.toString());
        Object obj = new Integer(32);
        System.out.println((String) obj);
        System.out.println(String.valueOf("null") == null);
    }

    @Test
    public void  test21(){
        Integer f1 = 100,f2 = 100, f3 = 150,f4 = 150;
        System.out.println(f1 == f2);//true
        System.out.println(f3 == f4);//false
        Integer i1 = 128;
        Integer i2 = 128;
        System.out.println(i1 == i2);
    }
    /**
     * 栈、堆、静态区
     */

}
