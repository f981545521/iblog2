package threadtest;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author youfang
 * @version [1.0.0, 2018-12-04 下午 03:52]
 **/
public class QueueTest {

    private static ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<>();

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            queue.add(i);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (queue.size() > 0){
                    int cur = (int) queue.poll();
                    System.out.println(Thread.currentThread().getName() + "-" + cur);
                    if (cur % 100 == 0){
                        try {
                            Thread.sleep(10 * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        Runnable runnable2 = new Runnable() {
            @Override
            public void run() {
                while (queue.size() > 0){
                    int cur = (int) queue.poll();
                    System.out.println(Thread.currentThread().getName() + "-"  + cur);
                    if (cur % 50 == 0){
                        try {
                            Thread.sleep(10 * 1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        System.out.println(queue.size());
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable2);
        t1.start();
        t2.start();

    }
}
