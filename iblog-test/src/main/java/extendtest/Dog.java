package extendtest;

/**
 * @author youfang
 * @date 2018-02-06 16:53
 **/
public class Dog extends Animal{

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void wangwang(){
        super.eat();
        System.out.println("旺旺！");
    }

    @Override
    void eat() {
        System.out.println("啃骨头。");
    }
}
