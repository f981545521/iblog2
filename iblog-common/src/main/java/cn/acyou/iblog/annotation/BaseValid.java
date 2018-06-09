package cn.acyou.iblog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-09 下午 12:22]
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface BaseValid {

    //是否可以为空, 默认不可以为空
    boolean nullable() default false;


    //参数或者字段描述,这样能够显示友好的异常信息
    String message() default "";

    //返回错误码信息
    String code() default "";

}
