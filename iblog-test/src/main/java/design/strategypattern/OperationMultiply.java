package design.strategypattern;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-09 下午 02:31]
 **/
public class OperationMultiply implements Strategy{
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
