package threadtest.staticthread;

/**
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:40]
 **/
public class StaticAction {
    public static int i = 0;
    public static void print() {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "step :" + i);
            sum += i;
        }
        if (sum != 45) {
            System.out.println("Thread error!");
            System.exit(0);
        }
        System.out.println(Thread.currentThread().getName() + " - sum is " + sum);
    }
}