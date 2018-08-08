package design.prototypepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-08 下午 02:22]
 **/
public class Circle extends Shape {

    public Circle(){
        type = "Circle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
