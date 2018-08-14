package threadtest.staticthread2;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:50]
 **/
public class StaticAction2 {
    public static int i = 0;
    public synchronized static void incValue() {
        int temp = StaticAction.i;
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        temp++;
        StaticAction.i = temp;
    }
}
