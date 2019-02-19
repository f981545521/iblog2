package concurrenttest;

import java.util.concurrent.Semaphore;

/**
 * Semaphore翻译成字面意思为 信号量，
 * Semaphore可以控同时访问的线程个数，通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 *
 * @author youfang
 * @version [1.0.0, 2019-01-26 下午 01:00]
 **/
public class SemaphoreTest {
    public static void main(String[] args) {
        //工人数
        int worker = 8;
        //机器数目
        //参数permits表示许可数目，即同时可以允许多少线程进行访问
        //参数fair表示是否是公平的，即等待时间越久的越先获取许可
        Semaphore semaphore = new Semaphore(5, true);
        for (int i = 0; i < worker; i++) {
            new Worker(i, semaphore).start();
        }
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;

        public Worker(int num, Semaphore semaphore) {
            this.num = num;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                //acquire()用来获取一个许可，若无许可能够获得，则会一直等待，直到获得许可。
                semaphore.acquire();
                System.out.println("工人" + this.num + "占用一个机器在生产...");
                Thread.sleep(2000);
                System.out.println("工人" + this.num + "释放出机器");
                //release()用来释放许可。注意，在释放许可之前，必须先获获得许可。
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
