package design.flyweightPattern;

import java.util.HashMap;

/**
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 10:30]
 **/
public class ShapeFactory {
    private static final HashMap<String, Shape> circleMap = new HashMap<>();

    public static Shape getCircle(String color) {
        Circle circle = (Circle)circleMap.get(color);

        if(circle == null) {
            circle = new Circle(color);
            circleMap.put(color, circle);
            System.out.println("Creating circle of color : " + color);
        }
        return circle;
    }
}
