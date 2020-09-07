package threadtest.performance;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 07:35]
 **/
public class PerformanceTest8 {

    public static Integer count = 0;

    public static synchronized void add(){
        PerformanceTest8.count += 1;
        System.out.println(PerformanceTest8.count);
    }

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getId() + "准备就绪了");
                try {
                    latch.await();
                    //业务代码
                    for (int j = 0; j < 10; j++) {
                        add();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getId() + "执行完成了！");
            }).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println("主线程完成！");

    }
}

