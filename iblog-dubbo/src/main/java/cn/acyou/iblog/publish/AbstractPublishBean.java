package cn.acyou.iblog.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author youfang
 * @date 2018-02-27 9:19
 **/
public abstract class AbstractPublishBean implements ApplicationContextAware {
    protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private static final List<TypeFilter> includeFilters = new LinkedList<>();
    protected String basePkg;
    protected ApplicationContext context;
    protected ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    protected MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);


    static {
        includeFilters.add(new AnnotationTypeFilter(Service.class));
    }

    @PostConstruct
    public void init() throws IOException {
        assert (basePkg != null);
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                resolveBasePackage(basePkg) + "/" + DEFAULT_RESOURCE_PATTERN;
        Resource[] resources = this.resourcePatternResolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            {
                try {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    if (isCandidateComponent(metadataReader)) {
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                        sbd.setResource(resource);
                        sbd.setSource(resource);
                        if (isCandidateComponent(sbd)) {
                            String clazzName = sbd.getBeanClassName();
                            Class clazz = null;
                            if (sbd.hasBeanClass()) {
                                clazz = sbd.getBeanClass();
                            } else {
                                clazz = Class.forName(clazzName);
                            }
                            String beanName = getBeanName(clazzName);
                            doPublish(clazz, beanName);
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        doCleanUp();
    }

    protected abstract void doCleanUp();

    protected abstract String getBeanName(String clazzName);

    protected abstract void doPublish(Class clazz, String beanName);

    public String getBasePkg() {
        return basePkg;
    }

    public void setBasePkg(String basePkg) {
        this.basePkg = basePkg;
    }

    protected BeanDefinitionRegistry getBeanDefinitionRegistry() {
        return (BeanDefinitionRegistry) ((AbstractRefreshableConfigApplicationContext) context).getBeanFactory();
    }

    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
        for (TypeFilter tf : includeFilters) {
            if (tf.match(metadataReader, this.metadataReaderFactory)) {
                return true;
            }
        }
        return false;
    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isConcrete() && beanDefinition.getMetadata().isIndependent());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(basePackage);
    }
}