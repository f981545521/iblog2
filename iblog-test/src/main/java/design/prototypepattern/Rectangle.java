package design.prototypepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-08 下午 02:22]
 **/
public class Rectangle extends Shape {

    public Rectangle(){
        type = "Rectangle";
    }

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
