package design.decoratorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-8-8]
 **/
public class Rectangle implements Shape {

    @Override
    public void draw() {
        System.out.println("Shape: Rectangle");
    }
}
