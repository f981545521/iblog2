package jsontest;

import java.io.Serializable;

/**
 * Result 泛型类
 * @author youfang
 * @version [1.0.0, 2018-09-30 下午 03:57]
 **/
public class ResultReference<T> implements Serializable {
    private static final long serialVersionUID = 110183987538722791L;

    /** 时间戳. */
    private Long timestamp;

    /** 返回码. */
    private String status = "";

    /** 结果信息. */
    private String message = "";

    /** 结果信息. */
    private T data;

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
