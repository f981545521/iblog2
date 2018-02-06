package extendtest;

/**
 * @author youfang
 * @date 2018-02-06 16:53
 **/
public class Animal{
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.wangwang();
    }

    void eat(){
        System.out.println("大家都要吃东西。");
    }
}
