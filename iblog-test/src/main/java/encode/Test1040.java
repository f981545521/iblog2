package encode;

/**
 * @author youfang
 * @version [1.0.0, 2019-2-13 下午 09:34]
 **/
public class Test1040 {

    private static long totalPeople = 1;
    private static long price = 69800L;
    private static long prevPeople = 1;
    public static void main(String[] args){
        long pullNumber = 3;
        long limit = 20;
        for (int i=1;i<limit;i++){
            long currentPeople = prevPeople * pullNumber;
            totalPeople+=currentPeople;
            System.out.println("当前层级：" + (i+1) + ";\t\t当前人数："
                    + currentPeople + ";\t\t参与总人数：" + totalPeople);
            prevPeople = currentPeople;

            long currentPrice = 69800 * currentPeople;
            price+=currentPrice;
            System.out.println("当前参与金额：" + currentPrice + "；\t\t参与总金额：" + price);
        }
    }

}
