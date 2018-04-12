package cn.acyou.iblog.exception;

/**
 * @author youfang
 * @date 2018-04-12 下午 10:05
 **/
public class SystemException extends RuntimeException{

    private static final long serialVersionUID = 5667579794329697168L;

    public SystemException(String message){
        super(message);
    }

    public SystemException(String message, Throwable cause){
        super(message, cause);
    }
}