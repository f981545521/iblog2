package locktest;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-07 上午 09:22]
 **/
public class SynchronizedTest implements Runnable{

    private static Integer count = 0;

    /**
     * 静态方法：对象锁就当前类对象，由于无论创建多少个实例对象，但对于的类对象拥有只有一个，所有在这样的情况下对象锁就是唯一的。
     *
     * 非静态方法：属于一个实例，main方法中创建了多个实例，
     *   因此t1和t2都会进入各自的对象锁，也就是说t1和t2线程使用的是不同的锁，因此线程安全是无法保证的。
     *   解决这种困境的的方式是将synchronized作用于静态的increase方法
     */
    public static synchronized void increase(){
        count++;
    }

    /**
     * 实例方法：锁住的是该类的实例对象
     */
    public synchronized void increaseMember(){
        count++;
    }

    public static void main(String[] args) {
        /* 测试静态方法：不需要是同一个实例 */
        /*for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new SynchronizedTest());
            thread.start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);*/

        /* 测试实例方法：要是同一个实例 */
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(synchronizedTest);
            thread.start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result: " + count);
    }


    @Override
    public void run() {
        for (int i = 0; i < 10000; i++){
            increaseMember();
        }
    }
}
