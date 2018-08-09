package design.strategypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 02:31]
 **/
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}
