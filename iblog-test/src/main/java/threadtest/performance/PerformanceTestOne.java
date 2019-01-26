package threadtest.performance;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 07:35]
 **/
public class PerformanceTestOne {

    private static List<Integer> actionType = Lists.newArrayList(1, 2, 3);
    private static List<Integer> shareIds = Lists.newArrayList(100, 101, 102, 103);
    private static List<Long> memberIds = Lists.newArrayList(10001L, 10002L, 10003L);

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        shareIds.forEach(shareId -> {
            memberIds.forEach(memberId -> {
                executor.submit(() -> {
                    PerformanceBean performanceBean = new PerformanceBean();
                    performanceBean.setShareId(shareId);
                    performanceBean.setMemberId(memberId);
                    System.out.println(performanceBean.showTime());
                });
            });
        });

        System.out.println("主线程完成");
        executor.shutdown();
    }
}
