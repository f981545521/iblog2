package design.visitorpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:46]
 **/
public class Mouse  implements ComputerPart {

    @Override
    public void accept(ComputerPartVisitor computerPartVisitor) {
        computerPartVisitor.visit(this);
    }
}
