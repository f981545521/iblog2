package concurrenttest;

import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-14 下午 04:20]
 **/
public class ConcurrentTest {

    @Test
    public void test1(){
        ThreadFactory threadFactory =  new NameTreadFactory();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), threadFactory);
        poolExecutor.getActiveCount();


    }
    static class NameTreadFactory implements ThreadFactory {

        private final AtomicInteger mThreadNum = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "my-thread-" + mThreadNum.getAndIncrement());
            System.out.println(t.getName() + " has been created");
            return t;
        }
    }

    @Test
    public void test2(){
        System.out.println(Math.PI);
    }

}
