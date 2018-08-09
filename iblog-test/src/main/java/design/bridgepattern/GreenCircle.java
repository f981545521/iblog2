package design.bridgepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 10:50]
 **/
public class GreenCircle implements DrawAPI {
    @Override
    public void drawCircle(int radius, int x, int y) {
        System.out.println("Drawing Circle[ color: green, radius: "
                + radius +", x: " +x+", "+ y +"]");
    }
}
