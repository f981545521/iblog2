package extendtest;

/**
 * @author youfang
 * @date 2018-03-03 12:10
 */
public class Wolf extends Zoo{
    private String name = "Wolf";

    public Wolf(){
        System.out.println("我是子类的构造方法");
        System.out.println(name);
    }
    @Override
    public void sleep() {
        System.out.println("狼晚上不睡觉");
    }

    public static void main(String[] args) {
/*        Zoo zoo = new Wolf();
        zoo.eat();
        zoo.sleep();*/
        Wolf wolf = new Wolf();
        wolf.sleep();
    }
}
