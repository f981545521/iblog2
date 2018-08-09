package design.bridgepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 10:49]
 **/
public class RedCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: red, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}
