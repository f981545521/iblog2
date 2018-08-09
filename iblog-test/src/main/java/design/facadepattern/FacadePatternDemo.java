package design.facadepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:00]
 **/
public class FacadePatternDemo {
    public static void main(String[] args) {
        ShapeMaker shapeMaker = new ShapeMaker();

        shapeMaker.drawCircle();
        shapeMaker.drawRectangle();
        shapeMaker.drawSquare();
    }
}
