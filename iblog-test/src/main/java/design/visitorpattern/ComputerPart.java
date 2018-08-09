package design.visitorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:45]
 **/
public interface ComputerPart {
    public void accept(ComputerPartVisitor computerPartVisitor);
}
