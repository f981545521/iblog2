package cn.acyou.exception;

/**
 * @author youfang
 * @date 2018-02-07 17:56
 **/
public class EmailErrorException extends RuntimeException{
    public EmailErrorException() {
    }

    public EmailErrorException(String message) {
        super(message);
    }

    public EmailErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailErrorException(Throwable cause) {
        super(cause);
    }

}
