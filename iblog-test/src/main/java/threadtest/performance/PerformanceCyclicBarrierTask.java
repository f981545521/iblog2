package threadtest.performance;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 07:37]
 **/
public class PerformanceCyclicBarrierTask implements Runnable {

    private Integer shareId;
    private Long memberId;

    private CyclicBarrier cyclicBarrier;
    private static Integer count = 0;

    PerformanceCyclicBarrierTask(Integer shareId, Long memberId, CyclicBarrier cyclicBarrier){
        this.shareId = shareId;
        this.memberId = memberId;
        this.cyclicBarrier = cyclicBarrier;
    }
    @Override
    public void run() {
        PerformanceBean performanceBean = new PerformanceBean();
        performanceBean.setShareId(shareId);
        performanceBean.setMemberId(memberId);
        System.out.println("线程" + Thread.currentThread().getId() + "准备完成！");
        // 等待所有任务准备就绪
        try {
            cyclicBarrier.await();
            //执行测试
            System.out.println(performanceBean.showTime() + " -> 当前计数：" + count);
            count ++;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
