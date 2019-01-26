package threadtest.performance;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 07:37]
 **/
public class PerformanceTask implements Runnable {

    private Integer shareId;
    private Long memberId;

    PerformanceTask(Integer shareId, Long memberId){
        this.shareId = shareId;
        this.memberId = memberId;
    }
    @Override
    public void run() {
        PerformanceBean performanceBean = new PerformanceBean();
        performanceBean.setShareId(shareId);
        performanceBean.setMemberId(memberId);
        System.out.println("线程" + Thread.currentThread().getId() + "准备完成！");
        System.out.println(performanceBean.showTime());
    }
}
