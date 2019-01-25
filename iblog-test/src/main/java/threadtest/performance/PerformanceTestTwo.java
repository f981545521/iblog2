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
public class PerformanceTestTwo {

    private static List<Integer> actionType = Lists.newArrayList(1, 2, 3);
    private static List<Integer> shareIds = Lists.newArrayList(100, 101, 102, 103);
    private static List<Long> memberIds = Lists.newArrayList(10001L, 10002L, 10003L);

    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                System.out.println("线程" + Thread.currentThread().getId() + "准备就绪了");
                try {
                    latch.await();
                    //业务代码
                    shareIds.forEach(shareId -> {
                        memberIds.forEach(memberId -> {
                            PerformanceBean performanceBean = new PerformanceBean();
                            performanceBean.setShareId(shareId);
                            performanceBean.setMemberId(memberId);
                            System.out.println(performanceBean.showTime());
                        });
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getId() + "执行完成了！");
            }).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
        System.out.println("主线程完成！");

    }
}
