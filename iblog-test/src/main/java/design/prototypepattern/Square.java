package design.prototypepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-08 下午 02:22]
 **/
public class Square extends Shape {

    public Square(){
        type = "Square";
    }

    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
