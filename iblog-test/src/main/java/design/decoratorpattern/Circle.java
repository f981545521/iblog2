package design.decoratorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 09:38]
 **/
public class Circle implements Shape {

    @Override
    public void draw() {
        System.out.println("Shape: Circle");
    }
}
