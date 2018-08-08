package design.commandPattern;

/**
 * 实现了 Order 接口的实体类。
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 10:54]
 **/
public class BuyStock implements Order {
    private Stock abcStock;

    public BuyStock(Stock abcStock){
        this.abcStock = abcStock;
    }

    public void execute() {
        abcStock.buy();
    }
}
