package threadtest.performance;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2019-01-25 下午 07:37]
 **/
public class PerformanceBean implements Serializable {
    private static final long serialVersionUID = 2404599300187022431L;

    private Integer shareId;

    private Long memberId;

    public Integer getShareId() {
        return shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String showTime(){
        return "分享Id：" + this.shareId + "|分享者：" + this.memberId;
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PerformanceBean{");
        sb.append("shareId=").append(shareId);
        sb.append(", memberId=").append(memberId);
        sb.append('}');
        return sb.toString();
    }
}
