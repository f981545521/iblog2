package java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-05 上午 11:22]
 **/
public class DoubleColon {

    public static void  printValur(String str){
        System.out.println("print value : "+str);
    }

    @Test
    public void test1(){
        List<String> al = Arrays.asList("a","b","c","d");
        for (String a: al) {
            DoubleColon.printValur(a);
        }
        //下面的for each循环和上面的循环是等价的
        al.forEach(x-> {
            DoubleColon.printValur(x);
        });
    }

    //JDK8中有双冒号的用法，就是把方法当做参数传到stream内部，使stream的每个元素都传入到该方法里面执行一下。
    @Test
    public void test11(){
        List<String> al = Arrays.asList("a", "b", "c", "d");
        //al.forEach(DoubleColon::printValur);
        //下面的方法和上面等价的
        Consumer<String> methodParam = DoubleColon::printValur; //方法参数
        al.forEach(x -> methodParam.accept(x));//方法执行accept
    }

    /**
     * 表达式:
     * person -> person.getAge();
     * 可以替换成
     * Person::getAge
     */
    @Test
    public void test2(){
        Map map = new HashMap<>();
    }
}
