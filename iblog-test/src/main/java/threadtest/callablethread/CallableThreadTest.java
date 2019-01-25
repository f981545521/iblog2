package threadtest.callablethread;

import design.mvcpattern.Student;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 02:51]
 **/
public class CallableThreadTest {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(new RunnableTask(2));

        // 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
        Future<Student> future = executor.submit(new CallableTask(1));
        executor.shutdown();
        if (future.isDone()) {
            System.out.println(future.get());
        }
    }

}
