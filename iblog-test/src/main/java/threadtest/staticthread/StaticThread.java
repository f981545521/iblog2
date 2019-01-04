package threadtest.staticthread;

/**
 * 1，java在执行静态方法时，会在内存中拷贝一份，如果静态方法所在的类里面没有静态的变量，那么线程访问就是安全的，
 * 比如在javaee中服务器必然会多线程的处理请求此时如果设计全局需要调用的静态方法，可用此种设计。
 *
 * 2，java在执行静态方法时，如果使用静态变量，同时类的函数设计时使用到了静态数据，
 * 最好在调用函数时使用synchronized关键字，否则会导致数据的不一致行。
 *
 * 3，加静态全局的变量，在多线程访问下定会出现数据的不一致行，最好使用synchronized关键字，确保数据的一致性，典型的代表就是单例模式。
 * @author youfang
 * @version [1.0.0, 2018-08-14 上午 09:35]
 **/
public class StaticThread implements Runnable {
    @Override
    public void run() {
        StaticAction.print();
    }
    public static void main(String[] args) {
        /**
         * 实际执行的结果显示各个线程对静态方法的访问是交叉执行的，但是这并不影响各个线程静态方法print()中sum值的计算。
         * 也就是说，在此过程中没有使用全局变量的静态方法在多线程中是安全的，
         * 静态方法是否引起线程安全问题主要看该静态方法是否对全局变量（静态变量static member）进行修改操作。
         */
        for (int i = 0; i < 10; i++) {
            new Thread(new StaticThread()).start();
        }
    }
}
