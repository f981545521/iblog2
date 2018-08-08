package design.decoratorpattern;

/**
 * 实现了 Shape 接口的抽象装饰类。
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 09:38]
 **/
public abstract class ShapeDecorator implements Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape){
        this.decoratedShape = decoratedShape;
    }

    public void draw(){
        decoratedShape.draw();
    }
}