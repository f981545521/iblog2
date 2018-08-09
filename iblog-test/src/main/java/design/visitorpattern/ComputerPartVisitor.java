package design.visitorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:46]
 **/
public interface ComputerPartVisitor {
    void visit(Computer computer);
    void visit(Mouse mouse);
    void visit(Keyboard keyboard);
    void visit(Monitor monitor);
}
