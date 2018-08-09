package design.factorypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:40]
 **/
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
