package threadtest.performance;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-26 上午 09:55]
 **/
public class PerformanceTestSeven {

    private static List<Integer> shareIds = Lists.newArrayList(100, 101, 102, 103, 104, 105, 106);
    private static List<Long> memberIds = Lists.newArrayList(10001L, 10002L, 10003L, 10004L, 10005L);

    public static void main(String[] args) {
        int count = 20;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        shareIds.forEach(shareId -> {
            memberIds.forEach(memberId -> {
                executorService.execute(new PerformanceCyclicBarrierTask(shareId, memberId, cyclicBarrier));
            });
        });

        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
