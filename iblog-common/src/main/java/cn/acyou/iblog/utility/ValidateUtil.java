/*
 * 文 件 名:  ValidUtil
 * 版    权:  Copyright 2018 南京慕冉信息科技有限公司,  All rights reserved
 * 描    述:  <描述>
 * 版    本： <1.0.0>
 * 创 建 人:  youfang
 * 创建时间:   2018-06-09

 */
package cn.acyou.iblog.utility;

import cn.acyou.iblog.annotation.BaseValid;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

/**
 * @author youfang
 * @version [1.0.0, 2018-06-09 下午 12:24]
 * @since [小倦鸟/远方模块]
 **/
public class ValidateUtil {
    private static final Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    /**
     * 验证解析
     */
    public static JsonResult valid(Object object) {
        //获取object的类型
        Class<? extends Object> clazz = object.getClass();
        //获取该类型声明的成员
        Field[] fields = clazz.getDeclaredFields();
        //遍历属性
        for (Field field : fields) {
            //对于private私有化的成员变量，通过setAccessible来修改器访问权限
            field.setAccessible(true);
            JsonResult result = validate(field, object);
            //重新设置会私有权限
            field.setAccessible(false);
            if (result != null){
                return result;
            }
        }
        return null;
    }

    /**
     * 验证注解
     */
    public static JsonResult validate(Field field, Object object) {
        Object value = null;
        String description;

        //获取对象的成员的注解信息
        BaseValid baseValid = field.getAnnotation(BaseValid.class);
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (baseValid == null) {
            return null;
        }

        String fieIdName = field.getName();
        description = baseValid.message();

        if (!baseValid.nullable()) {
            if (null == value || StringUtils.isBlank(value.toString())) {
                description = "信息不完整";
                return new JsonResult(500, description);
            }
        }

        return null;

    }

}
