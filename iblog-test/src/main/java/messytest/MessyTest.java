package messytest;

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
}
