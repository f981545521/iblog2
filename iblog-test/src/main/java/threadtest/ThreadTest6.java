package threadtest;

/**
 * synchronized修饰类
 * @author youfang
 * @date 2018-03-28 下午 07:05
 **/
public class ThreadTest6 {

    public static void main(String[] args) {

        /**
         * synchronized作用于一个类T时，是给这个类T加锁，T的所有对象用的是同一把锁。
         */
        SyncThread6 syncThread1 = new SyncThread6();
        SyncThread6 syncThread2 = new SyncThread6();
        Thread thread1 = new Thread(syncThread1, "SyncThread1");
        Thread thread2 = new Thread(syncThread2, "SyncThread2");
        thread1.start();
        thread2.start();
    }
}
/**
 * 同步线程
 */
class SyncThread6 implements Runnable {
    private static int count;

    public SyncThread6() {
        count = 0;
    }

    public static void method() {
        synchronized(SyncThread.class) {
            for (int i = 0; i < 5; i ++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void run() {
        method();
    }
}
