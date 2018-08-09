package design.bridgepattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 10:50]
 **/
public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Shape(DrawAPI drawAPI){
        this.drawAPI = drawAPI;
    }
    public abstract void draw();
}
