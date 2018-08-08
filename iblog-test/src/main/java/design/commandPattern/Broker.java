package design.commandPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令调用类。
 * @author youfang
 * @version [1.0.0, 2018-8-8 下午 10:55]
 **/

public class Broker {
    private List<Order> orderList = new ArrayList<Order>();

    public void takeOrder(Order order){
        orderList.add(order);
    }

    public void placeOrders(){
        for (Order order : orderList) {
            order.execute();
        }
        orderList.clear();
    }
}
