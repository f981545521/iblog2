package realtime.article;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author youfang
 * @version [1.0.0, 2019-03-01 下午 05:08]
 **/
public class Article implements Serializable {
    private static final long serialVersionUID = -6014013459962319612L;

    /**
     * 文章ID
     */
    private Long id;

    /**
     * 发布方式，1立即，2定时
     */
    private Integer pubType;

    /**
     * 定时发布的时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date pubTime;

    /**
     * 状态：0 未发布，1：已发布
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPubType() {
        return pubType;
    }

    public void setPubType(Integer pubType) {
        this.pubType = pubType;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", pubType=").append(pubType);
        sb.append(", pubTime=").append(pubTime);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }
}
