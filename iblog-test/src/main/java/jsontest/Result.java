package jsontest;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2018-09-30 下午 03:50]
 **/
public class Result implements Serializable {
    private static final long serialVersionUID = 6113597900268557137L;

    private Long timestamp;
    private String status = "";
    private String message = "";
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
