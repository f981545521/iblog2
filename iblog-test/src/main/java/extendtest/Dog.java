package extendtest;

/**
 * @author youfang
 * @date 2018-02-06 16:53
 **/
public class Dog extends Animal{
    public static void main(String[] args) {

    }

    public void wangwang(){
        super.eat();
        System.out.println("旺旺！");
    }


}
