package design.facadepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 10:59]
 **/
public class Square implements Shape {

    @Override
    public void draw() {
        System.out.println("Square::draw()");
    }
}