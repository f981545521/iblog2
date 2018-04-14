package cn.acyou.iblog.utility;

import java.io.Serializable;

public class JsonResult implements Serializable {

    private static final long serialVersionUID = 1924314756177998342L;
    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private int state;//状态标识
    private String message;//错误信息
    private Object data;//具体数据

    //若自己写了构造，则不再默认提供无参构造器！！！（重要）
    public JsonResult() {

    }
    /**
     * @param state   状态码
     * @param message 输出信息
     */
    public JsonResult(int state, String message) {
        this.state = state;
        this.message = message;
    }


    //错误情况
    public JsonResult(Throwable e) {
        state = ERROR;
        message = e.getMessage();
    }

    //正确情况
    public JsonResult(Object data) {
        state = SUCCESS;
        this.data = data;
    }

    /**
     * 单独返回字符串
     */
    public JsonResult(String message) {
        state = SUCCESS;
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    @Override
    public String toString() {
        return "JsonResult [state=" + state + ", message=" + message + ", data=" + data + "]";
    }


}
