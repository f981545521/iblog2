package design.interpreterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:25]
 **/
public interface Expression {
    public boolean interpret(String context);
}
