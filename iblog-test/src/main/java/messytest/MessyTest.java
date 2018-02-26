package messytest;

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
}
