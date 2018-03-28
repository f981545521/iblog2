package threadtest;

/**
 * @author youfang
 * @date 2018-03-28 下午 05:45
 **/
public class ThreadTest2 implements Runnable {


    public static void main(String[] args) {
        /**
         * 当两个并发线程(thread1和thread2)访问同一个对象(syncThread)中的synchronized代码块时，
         * 在同一时刻只能有一个线程得到执行，另一个线程受阻塞，必须等待当前线程执行完这个代码块以后才能执行该代码块。
         * Thread1和thread2是互斥的，因为在执行synchronized代码块时会锁定当前的对象，
         * 只有执行完该代码块才能释放该对象锁，下一个线程才能执行并锁定该对象。
         *
         * 所以：需要thread1执行完毕后释放锁，Thread2才可以执行。
         */
        //ThreadTest2 threadTest2 = new ThreadTest2();
        //Thread thread1 = new Thread(threadTest2, "SyncThread1");
        //Thread thread2 = new Thread(threadTest2, "SyncThread2");
        //thread1.start();
        //thread2.start();


        /**
         * thread1和thread2同时在执行。这是因为synchronized只锁定对象，每个对象只有一个锁（lock）与之相关联
         * 这时创建了两个SyncThread的对象syncThread1和syncThread2，
         * 线程thread1执行的是syncThread1对象中的synchronized代码(run)，而线程thread2执行的是syncThread2对象中的synchronized代码(run)；
         * 我们知道synchronized锁定的是对象，这时会有两把锁分别锁定syncThread1对象和syncThread2对象，
         * 而这两把锁是互不干扰的，不形成互斥，所以两个线程可以同时执行。
         *
         * 所以：thread1和thread2属于不同的线程，可以同时执行。
         */
        //Thread thread1 = new Thread(new ThreadTest2(), "SyncThread1");
        //Thread thread2 = new Thread(new ThreadTest2(), "SyncThread2");
        //thread1.start();
        //thread2.start();

    }
    private static int count;

    public ThreadTest2() {
        count = 0;
    }

    public  void run() {
        synchronized(this) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ":" + (count++));
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount() {
        return count;
    }
}