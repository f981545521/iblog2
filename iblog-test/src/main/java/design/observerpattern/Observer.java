package design.observerpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 01:24]
 **/
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
