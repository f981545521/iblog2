package design.commandPattern;

/**
 * 实现了 Order 接口的实体类。
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 10:55]
 **/
public class SellStock implements Order {
    private Stock abcStock;

    public SellStock(Stock abcStock){
        this.abcStock = abcStock;
    }

    public void execute() {
        abcStock.sell();
    }
}

