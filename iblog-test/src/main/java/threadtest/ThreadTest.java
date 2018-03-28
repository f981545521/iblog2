package threadtest;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author youfang
 * @date 2018-03-28 上午 11:00
 **/
public class ThreadTest {

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

}
