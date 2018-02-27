package cn.acyou.iblog.dubbo;

import cn.acyou.iblog.publish.AbstractPublishBean;
import com.alibaba.dubbo.config.spring.ServiceBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

/**
 * @author youfang
 * @date 2018-02-27 9:27
 **/
public class PublishDubboBean extends AbstractPublishBean {

    @Override
    protected void doCleanUp() {
        try {
            context.getBeansOfType(ServiceBean.class);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    protected String getBeanName(String clazzName) {
        return DubboNameGenerator.getBeanName(clazzName.substring(clazzName.lastIndexOf(".") + 1));
    }

    @Override
    protected void doPublish(Class clazz, String beanName) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ServiceBean.class);
        if (clazz.getInterfaces().length == 0) {
            logger.warn("skip publish dubbo service:" + beanName + " because no interface found!");
            return;
        }
        builder.setLazyInit(false);
        builder.addPropertyValue("interfaceClass", clazz.getInterfaces()[0]);
        builder.addPropertyReference("registry", "iblog-registry");
        builder.addPropertyReference("protocol", "iblog-protocol");
        builder.addPropertyReference("application", "iblog-application");
        builder.addPropertyValue("ref", context.getBean(clazz));
        builder.setInitMethodName("export");
        builder.setDestroyMethodName("unexport");
        getBeanDefinitionRegistry().registerBeanDefinition(beanName, builder.getRawBeanDefinition());
        logger.info("publish dubbo service:" + beanName);

    }

}