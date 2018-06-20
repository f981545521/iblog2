package threadtest;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * java主线程等待所有子线程执行完毕在执行
 *
 * 1. 用sleep方法，让主线程睡眠一段时间，当然这个睡眠时间是主观的时间，是我们自己定的，这个方法不推荐，但是在这里还是写一下，毕竟是解决方法
 * 2. 使用Thread的join()等待所有的子线程执行完毕，主线程在执行，thread.join()把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
 * 3. 等待多线程完成的CountDownLatch
 * 4. 同步屏障CyclicBarrier
 *
 *
 * @author youfang
 * @version [1.0.0, 2018-06-20 下午 05:52]
 **/
public class MainChildThreadTest {
    public static void main(String[] args) throws Exception {
        final List<String> videoIds = Lists.newArrayList();
        videoIds.add("972ea857d9e84ed29e42b9818a922cdc");
        videoIds.add("a7da73d3aa1345bcabd8da050604e634");
        videoIds.add("9088f7516a4e4a7ea207de7d0d5e89b3");
        videoIds.add("ef6a896c26244ca5b3731a3926da64b5");
        videoIds.add("662221f1dfba4f9dadbdb6d7ec32d79e");
        videoIds.add("ee0ac2ab89f64b2fb59105f0241d28b7");
        videoIds.add("f2d73c36b0d44fddbfb19a9092238cdf");
        videoIds.add("ff960911eecd4a5ba76976eaa8834e97");
        videoIds.add("e5c720113850473e80e4a8c7c8a22208");
        videoIds.add("83a8ded19c934029b4166eb4b37172f1");
        final Random random = new Random();
        System.out.println("主线程开始执行....");

        //1、 创建CountDownLatch 对象， 设定需要计数的子线程数目
        final CountDownLatch latch = new CountDownLatch(50);
        //final CyclicBarrier barrier = new CyclicBarrier(10);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i =0 ; i< 10; i ++){
                    int index = random.nextInt(videoIds.size());
                    String videoId = videoIds.get(index);
                    System.out.println(Thread.currentThread().getName() + ":" + videoId);
                    latch.countDown();
                }
            }
        };
        //线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        Thread thread3 = new Thread(runnable);
        Thread thread4 = new Thread(runnable);
        Thread thread5 = new Thread(runnable);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        //将线程加入线程池中
        //threadPool.execute(thread1);
        //threadPool.execute(thread2);
        //threadPool.execute(thread3);
        //threadPool.execute(thread4);
        //threadPool.execute(thread5);
        //当线程池中的任务全部执行完毕后，关闭线程池
        //threadPool.shutdown();
        latch.await();
        //使用Thread类的join方法，让主线程等待子线程完成后再执行：注意：使用线程池后无法使用JOIN
        //thread1.join();
        //thread2.join();
        //thread3.join();
        //thread4.join();
        //thread5.join();
        //barrier.await();
        System.out.println("主线程执行完毕....");

    }
}
