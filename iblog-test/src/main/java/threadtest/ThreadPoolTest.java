package threadtest;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author youfang
 * @date 2018-03-28 上午 11:00
 **/
public class ThreadPoolTest {

    @Test
    public void test1(){
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++){
                    if ( i == 50){
                        try {
                            Thread.sleep(30L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(i);
                }
            }
        };
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                for (int i = 65; i < 121; i++){
                    System.out.println((char)i);
                }
            }
        };
        executorService.execute(r1);
        executorService.execute(r2);
        executorService.shutdown();
    }

    @Test
    public void test2(){
        //原来Junit只管自己的运行，就是说当Junit执行完毕后，就会关闭程序，不会关心是否还有自己启动的后台线程在运行。
        // 当Junit运行完毕后，如果后台线程还没有执行完毕，那么也是不会再执行了
        SyncThread syncThread = new SyncThread();
        Thread thread1 = new Thread(syncThread, "SyncThread1");
        Thread thread2 = new Thread(syncThread, "SyncThread2");
        thread1.start();
        thread2.start();
    }

}
/**
 * 同步线程
 */
class SyncThread implements Runnable {
    private static int count;

    public SyncThread() {
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