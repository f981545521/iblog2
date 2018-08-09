package design.abstractfactorypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 09:46]
 **/
public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape) ;
}