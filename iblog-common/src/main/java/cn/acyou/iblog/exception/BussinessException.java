package cn.acyou.iblog.exception;

/**
 * @author youfang
 * @date 2018-02-25 14:32
 **/
public class BussinessException extends RuntimeException{
    public BussinessException(String message) {
        super(message);
    }

    public BussinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BussinessException() {
    }
}
