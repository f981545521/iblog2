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

    @Test
    public void test4(){
        //Dog dog = new Dog();
        //dog.wangwang();
        Animal animal = new Dog();
        animal.eat();

    }


    @Test
    public void test5(){
        Dog dog = new Dog();
        commonMethod(dog);
    }

    private void commonMethod(Animal animal){
        animal.eat();
    }



}
