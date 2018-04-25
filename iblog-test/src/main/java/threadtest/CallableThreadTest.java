package threadtest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Runnable和Callable的区别是，
 * (1)Callable规定的方法是call() 来自concurrent包,Runnable规定的方法是run() 来自lang包
 * (2)Callable的任务执行后可返回值 （利用FutureTask创建Runnable任务给Thread，FutureTask可以get线程返回值），而Runnable的任务是不能返回值得
 * (3)call方法可以抛出异常，run方法不可以
 * @author youfang
 * @date 2018-04-25 下午 09:14
 **/
public class CallableThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                for (int i = 0; i < 100; i++){
                    System.out.println(i);
                    if (i == 66){
                        return "我就是返回值";
                    }
                }
                return "0";
            }
        };
        FutureTask<String> task = new FutureTask<>(callable);
        Thread thread = new Thread(task);
        thread.start();
        String result = task.get();
        System.out.println(result);
    }
}
