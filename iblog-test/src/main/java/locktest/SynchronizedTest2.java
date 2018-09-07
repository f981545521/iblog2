package locktest;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-07 上午 09:38]
 **/
public class SynchronizedTest2 implements Runnable {
    static SynchronizedTest2 instance = new SynchronizedTest2();
    static int i = 0;

    @Override
    public void run() {
        //省略其他耗时操作....
        //使用同步代码块对变量i进行同步操作,锁对象为instance
        synchronized (instance) {
            for (int j = 0; j < 1000000; j++) {
                i++;
            }
        }
    }

    /**
     * 将synchronized作用于一个给定的实例对象instance，即当前实例对象就是锁对象，
     * 每次当线程进入synchronized包裹的代码块时就会要求当前线程持有instance实例对象锁，
     * 如果当前有其他线程正持有该对象锁，那么新到的线程就必须等待，这样也就保证了每次只有一个线程执行i++;操作。
     *
     *
     * //this,当前实例对象锁
     * synchronized(this){
     *     for(int j=0;j<1000000;j++){
     *         i++;
     *     }
     * }
     *
     * //class对象锁
     * synchronized(AccountingSync.class){
     *     for(int j=0;j<1000000;j++){
     *         i++;
     *     }
     * }
     */
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
