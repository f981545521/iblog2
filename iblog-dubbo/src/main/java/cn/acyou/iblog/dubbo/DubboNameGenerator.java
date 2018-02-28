package cn.acyou.iblog.dubbo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.util.ClassUtils;

import java.beans.Introspector;

/**
 * @author youfang
 * @date 2018-02-27 9:28
 **/
public class DubboNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        return getBeanName(definition.getBeanClassName());
    }

    static String getBeanName(String className) {
        String shortClassName = ClassUtils.getShortName(className);
        String beanName = "iblog" + StringUtils.removeEnd(shortClassName, "Impl") + "DubboFacade";
        return Introspector.decapitalize(beanName);
    }
}