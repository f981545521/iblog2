package cn.acyou.iblog.exception;

/**
 * @author youfang
 * @date 2018-02-25 14:32
 **/
public class BusinessException extends RuntimeException{
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException() {
    }
}
