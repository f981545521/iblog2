package threadtest;

/**
 * synchronized修饰方法
 * @author youfang
 * @date 2018-03-28 下午 06:14
 **/
public class ThreadTest5 {
    public static void main(String[] args) {
        /**
         * syncThread1和syncThread2是SyncThread的两个对象，但在thread1和thread2并发执行时却保持了线程同步。
         * 这是因为run中调用了静态方法method，而静态方法是属于类的，所以syncThread1和syncThread2相当于用了同一把锁。
         */
        SyncThread3 syncThread1 = new SyncThread3();
        SyncThread3 syncThread2 = new SyncThread3();
        Thread thread1 = new Thread(syncThread1, "SyncThread1");
        Thread thread2 = new Thread(syncThread2, "SyncThread2");
        thread1.start();
        thread2.start();
    }

}
/**
 * 同步线程
 */
class SyncThread3 implements Runnable {
    private static int count;

    public SyncThread3() {
        count = 0;
    }

    public synchronized static void method() {
        for (int i = 0; i < 5; i ++) {
            try {
                System.out.println(Thread.currentThread().getName() + ":" + (count++));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void run() {
        method();
    }
}










//在子类方法中加上synchronized关键字
class Parent {
    public synchronized void method() { }
}
class Child extends Parent {
    public synchronized void method() {}
}

//在子类方法中调用父类的同步方法
class Parent2 {
    public synchronized void method() {   }
}
class Child2 extends Parent2 {
    public void method() { super.method();   }
}