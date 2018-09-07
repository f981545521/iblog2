package locktest;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-07 上午 09:58]
 **/
public class SynchronizedBlock implements Runnable{

    private static Integer count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new SynchronizedBlock());
        Thread t2 = new Thread(new SynchronizedBlock());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("运行结束" + count);
    }

    @Override
    public void run() {
        synchronized (SynchronizedBlock.class){
            System.out.println(Thread.currentThread().getName() + " - " + count);
            for (int i = 0; i < 1000; i++) {
                if (i == 500){
                    try {
                        Thread.sleep(5000);
                    }catch (InterruptedException e){
                        System.out.println(e.getMessage());
                    }
                }
                count++;
            }
        }
    }
}
