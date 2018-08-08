package design.decoratorpattern;

/**
 * 扩展了 ShapeDecorator 类的实体装饰类。
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 09:39]
 **/
public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape decoratedShape){
        System.out.println("Border Color: Red");
    }
}
