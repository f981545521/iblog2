package extendtest;

/**
 * @author youfang
 * @date 2018-03-03 12:08
 */
public abstract class Zoo {

    private String name = "Zoo";

    Zoo(){
        System.out.println(name);
    }

    public void eat() {
        System.out.println("动物园饲养动物");
    }

    public abstract void sleep();
}
