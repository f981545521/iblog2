package cn.acyou.iblog.model.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 此类跟踪Bean的生命周期
 * @author youfang
 * @date 2018-04-04 下午 05:41
 **/
@Component
public class LifeBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, BeanPostProcessor, InitializingBean, DisposableBean {
    private String name;
    private LifeBean lb;

    public LifeBean getLb() {
        return lb;
    }
    public void setLb(LifeBean lb) {
        this.lb = lb;
    }
    public LifeBean(){
        System.out.println("LifeBean() 构造器");
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("setName() 方法");
        this.name = name;
    }

    public void init(){
        System.out.println("this is init in lifeBean");
    }

    public void destory(){
        System.out.println("this is destory in lifeBean");
    }

    public String sayHello(){
        return "Hello";
    }

    @Override
    public void setBeanName(String name) {
        /**
         * 如果Bean实现了BeanNameAware接口，Spring将Bean的ID传递给setBeanName()方法
         * （实现BeanNameAware清主要是为了通过Bean的引用来获得Bean的ID，一般业务中是很少有用到Bean的ID的）
         */
        System.out.println("BeanNameAware的方法：" + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        /**
         * 如果Bean实现了BeanFactoryAware接口，Spring将调用setBeanDactory(BeanFactory bf)方法并把BeanFactory容器实例作为参数传入。
         * （实现BeanFactoryAware 主要目的是为了获取Spring容器，如Bean通过Spring容器发布事件等）
         */
        System.out.println("BeanFactoryAware的方法");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        /**
         * 如果Bean实现了ApplicationContextAwaer接口，Spring容器将调用setApplicationContext(ApplicationContext ctx)方法，
         * 把y应用上下文作为参数传入.(作用与BeanFactory类似都是为了获取Spring容器，
         * 不同的是Spring容器在调用setApplicationContext方法时会把它自己作为setApplicationContext 的参数传入，
         * 而Spring容器在调用setBeanDactory前需要程序员自己指定（注入）setBeanDactory里的参数BeanFactory )
         */
        System.out.println("ApplicationContextAwaer的方法");
    }


    /**
     * postProcessBeforeInitialization（预初始化）方法（作用是在Bean实例创建成功后对进行增强处理，如对Bean进行修改，增加某个功能）
     * 每个Bean的创建之前/创建之后都会调用这个方法
     * @param bean Bean
     * @param beanName Bean Name
     * @return Bean
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //Bean初始化之前
        //System.out.println("BeanPostProcessor  Before" + beanName);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //Bean初始化之后
        //System.out.println("BeanPostProcessor  After" + beanName);
        return bean;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /**
         * 如果Bean实现了InitializingBean接口，Spring将调用它们的afterPropertiesSet方法，
         * 作用与在配置文件中对Bean使用init-method声明初始化的作用一样，都是在Bean的全部属性设置成功后执行的初始化方法。
         */
        System.out.println("InitializingBean 的方法：");
        this.name = "小三";
        this.lb = new LifeBean();
    }

    /**
     * Bean定义文件中定义destroy-method，在容器关闭时，可以在Bean定义文件中使用“destory-method”定义的方法
     * 或者如果Bean实现了DispostbleBean接口，Spring将调用它的destory方法，
     * 作用与在配置文件中对Bean使用destory-method属性的作用一样，都是在Bean实例销毁前执行的方法。
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        System.out.println("调用DisposableBean 的方法销毁Bean");
    }
}
