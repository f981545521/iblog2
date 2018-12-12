package threadtest;

import java.util.concurrent.CountDownLatch;

/**
 * 就是N个线程执行操作，主线程等到N个子线程执行完毕之后，在继续往下执行。
 *
 * CountDownLatch典型用法
 * 1：某一线程在开始运行前等待n个线程执行完毕。将CountDownLatch的计数器初始化为n new CountDownLatch(n) ，
 * 每当一个任务线程执行完毕，就将计数器减1 countdownlatch.countDown()，
 * 当计数器的值变为0时，在CountDownLatch上 await() 的线程就会被唤醒。
 * 一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
 *
 * CountDownLatch典型用法
 * 2：实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
 * 类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。做法是初始化一个共享的CountDownLatch(1)，
 * 将其计数器初始化为1，多个线程在开始执行任务前首先 coundownlatch.await()，
 * 当主线程调用 countDown() 时，计数器变为0，多个线程同时被唤醒。
 * ---------------------
 * 作者：joenqc
 * 来源：CSDN
 * 原文：https://blog.csdn.net/joenqc/article/details/76794356
 * 版权声明：本文为博主原创文章，转载请附上博文链接！
 * @author youfang
 * @version [1.0.0, 2018-12-12 下午 01:46]
 **/
public class CountDownLatchTest {
    public static void main(String[] args) {
        //testCountDownLatch();
        testStartingGun();
    }

    public static void testCountDownLatch() {
        int threadCount = 10;
        final CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程" + Thread.currentThread().getId() + "开始出发");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getId() + "已到达终点");
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("10个线程已经执行完毕！开始计算排名");
    }

    /**
     * 发令枪
     */
    public static void testStartingGun() {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getId() + "准备就绪");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getId() + "执行完成！");
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
