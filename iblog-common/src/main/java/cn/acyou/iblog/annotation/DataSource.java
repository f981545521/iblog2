package cn.acyou.iblog.annotation;

import java.lang.annotation.*;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-14 下午 05:37]
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataSource {
    DataSourceType value() default DataSourceType.NORMAL;
}
