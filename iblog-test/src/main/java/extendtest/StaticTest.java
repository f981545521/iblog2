package extendtest;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-21 下午 08:08]
 **/
public class StaticTest {

    private static String a = initStaticString();

    private static String initStaticString(){
        System.out.println("初始化静态变量");
        return "youfang";
    }

    public static char getSomeThing(int index){
        return a.charAt(index);
    }

    public static void main(String[] args) {
        System.out.println(StaticTest.getSomeThing(2));
        System.out.println(StaticTest.getSomeThing(3));
        System.out.println(StaticTest.getSomeThing(4));
    }
}
