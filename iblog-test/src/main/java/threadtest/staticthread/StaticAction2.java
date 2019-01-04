package threadtest.staticthread;

/**
 * 使用全局静态变量
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:40]
 **/
public class StaticAction2 {
    public static int sum = 0;
    public static void print() {
        for (int i = 0; i <= 100; i++) {
            System.out.println(Thread.currentThread().getName() + "step :" + i + " - sum now :" + sum);
            sum += i;
        }
        System.out.println(Thread.currentThread().getName() + " - sum is " + sum);
    }
}