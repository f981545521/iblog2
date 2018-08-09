package design.interpreterpattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 11:26]
 **/
public class TerminalExpression implements Expression {

    private String data;

    public TerminalExpression(String data){
        this.data = data;
    }

    @Override
    public boolean interpret(String context) {
        if(context.contains(data)){
            return true;
        }
        return false;
    }
}
