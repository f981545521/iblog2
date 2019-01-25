package threadtest.performance;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 07:35]
 **/
public class PerformanceTestFive {

    private static List<Integer> shareIds = Lists.newArrayList(100, 101, 102, 103, 104, 105, 106);
    private static List<Long> memberIds = Lists.newArrayList(10001L, 10002L, 10003L, 10004L, 10005L);

    private static Integer count = 0;

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        ExecutorService executor = Executors.newFixedThreadPool(20);
        shareIds.forEach(shareId -> {
            memberIds.forEach(memberId -> {
                executor.submit(() -> {
                    PerformanceBean performanceBean = new PerformanceBean();
                    performanceBean.setShareId(shareId);
                    performanceBean.setMemberId(memberId);
                    System.out.println("线程" + Thread.currentThread().getId() + "准备完成！");
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count ++;
                    System.out.println(performanceBean.showTime() + "count=" + count);
                });
            });
        });
        try {
            System.out.println("等待线程就绪...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println("主线程完成");
        executor.shutdown();
    }
}
