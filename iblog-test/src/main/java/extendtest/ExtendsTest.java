package extendtest;

import org.junit.Test;

/**
 * @author youfang
 * @date 2018-02-06 18:20
 **/
public class ExtendsTest {

    @Test
    public void test1(){
        System.out.println("gogo simida");
    }

    @Test
    public void test2(){
        Dog dog = null;
        String name = dog.getName();
        System.out.println(name);
    }

    @Test
    public void test3(){
        Zoo zoo = new Wolf();
    }



}
