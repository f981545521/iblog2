package threadtest;

import org.junit.Test;
import org.junit.experimental.theories.Theories;

/**
 * @author youfang
 * @date 2018-04-02 下午 02:35
 **/
public class BaseThreadTest {
    public static void main(String[] args) throws Exception{
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++){
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 65; i < 121; i++){
                    System.out.println(Thread.currentThread().getName() + ":" + (char)i);
                }
            }
        };
        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        t1.start();
        t2.start();

        t2.join();//t1线程和t2线程交叉执行，当执行到t2.join()时，main线程等待t2执行完成，但是t1和t2依然是竞争关系，等t2执行完成之后，main线程会开始执行，如果t1这个时候还没有完成，则t1又会和main线程交叉执行。
        System.out.println("main 完成！");
        Thread.sleep(23);
        System.out.println("main 结束！");
    }
}
