package jsontest;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-30 下午 04:10]
 **/
public class FollowAndFans implements Serializable {
    private static final long serialVersionUID = -8580937476762069281L;
    private Long followCount = 0L;
    private Long fansCount = 0L;

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }

    public Long getFansCount() {
        return fansCount;
    }

    public void setFansCount(Long fansCount) {
        this.fansCount = fansCount;
    }
}
