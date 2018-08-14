package threadtest.staticthread2;

/**
 * 加入synchronized关键字的静态方法称为同步静态方法。
 * 在访问同步静态方法时，会获取该类的“Class”对象，所以当一个线程进入同步的静态方法中时，线程监视器获取类本身的对象锁，
 * 其它线程不能进入这个类的任何静态同步方法。它不像实例方法，因为多个线程可以同时访问不同实例同步实例方法。
 * 这个其实就是操作系统中的用信号量实现进程的互斥与同步问题，如果涉及在同一个类中有多个静态方法中处理多线程共享数据的话，
 * 那就变成用信号量解决生产者-消费者问题。也就是说，静态方法是一份临界资源，对静态方法的访问属于进入临界区；
 * 对静态变量的修改是一份临界资源，对静态变量的修改属于进入临界区。
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:51]
 **/
public class StaticThread2  implements Runnable {
    @Override
    public void run() {
        StaticAction2.incValue();
    }
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new StaticThread()).start();
        }
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(StaticAction2.i);
    }
}