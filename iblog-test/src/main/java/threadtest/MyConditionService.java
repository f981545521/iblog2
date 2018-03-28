package threadtest;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author youfang
 * @date 2018-03-28 下午 07:16
 **/
public class MyConditionService {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();
    Lock lock = new ReentrantLock();    //注意这个地方
    public static void main(String[] args)  {
        final MyConditionService test = new MyConditionService();

        new Thread(){
            public void run() {
                test.insert(Thread.currentThread());
            };
        }.start();

        new Thread(){
            public void run() {
                test.insert(Thread.currentThread());
            };
        }.start();
    }

    public void insert(Thread thread) {

        lock.lock();
        try {
            System.out.println(lock.toString());
            System.out.println(thread.getName()+"得到了锁");
            for(int i=0;i<5;i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }finally {
            System.out.println(thread.getName()+"释放了锁");
            lock.unlock();
        }
    }
}
