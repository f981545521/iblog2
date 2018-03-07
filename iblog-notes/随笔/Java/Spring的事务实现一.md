背景
今天看到项目中一段代码

@Transactional(propagation = Propagation.REQUIRES_NEW)
private TmShowConfig initTmShowConfig(String userId, String configType)
有没有感觉到哪里有问题？

分析
我们在使用Spring做aop的时候 无非两种代理模式

基于jdk的proxy 动态代理小结之proxy

我们来看Spring按照什么策略生成代理

public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

   @Override
   public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
      if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
         Class<?> targetClass = config.getTargetClass();
         if (targetClass == null) {
            throw new AopConfigException("TargetSource cannot determine target class: " +
                  "Either an interface or a target is required for proxy creation.");
         }
         if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
            return new JdkDynamicAopProxy(config);
         }
         return new ObjenesisCglibAopProxy(config);
      }
      else {
         return new JdkDynamicAopProxy(config);
      }
   }
}
很明显默认情况下均使用JdkDynamicAopProxy 当ProxyTargetClass开启是 如果对象不是借口的话将会返回ObjenesisCglibAopProxy 否则仍然返回JdkDynamicAopProxy

CGlib 我们都了解了 但是ObjenesisCglibAopProxy 是个啥呢？ 在Spring4之后默认采用了Objenesis这样的实现方式。

Java already supports this dynamic instantiation of classes using Class.newInstance(). However, this only works if the class has an appropriate constructor. There are many times when a class cannot be instantiated this way, such as when the class contains:

Constructors that require arguments.
Constructors that have side effects.
Constructors that throw exceptions.
As a result, it is common to see restrictions in libraries stating that classes must require a default constructor. Objenesis aims to overcome these restrictions by bypassing the constructor on object instantiation.

这个对我们的影响是啥呢？从此cglib的代理类不再需要一定提供无参构造函数了！！！【不过思考final域怎么办】

通常我们提到aop免不了提到的是如下几个关键词

advisor
pointcut
introduction
advice
具体继承图如下

f6car > Spring的事务实现一 > image2017-12-19 16:34:21.png

简单解释一下

advisor称之为切面 该接口中包含了advice
pointcut 是一种切面通常描述了那些被增强的方法
introduction称之为引入 也是一种切面 通常会给实现类增加额外的接口方法
advice 称之为增强 spring直接使用了aspectj的定义
我们最常使用的是pointcut 一般用来拦截某些指定的方法实现切面编程！

那么就可以提到本次关于事务的切面是如何实现的！

事务的切面定义如下

public class TransactionAttributeSourceAdvisor extends AbstractPointcutAdvisor {

   private TransactionInterceptor transactionInterceptor;

   private final TransactionAttributeSourcePointcut pointcut = new TransactionAttributeSourcePointcut() {
      @Override
      protected TransactionAttributeSource getTransactionAttributeSource() {
         return (transactionInterceptor != null ? transactionInterceptor.getTransactionAttributeSource() : null);
      }
   };


   /**
    * Create a new TransactionAttributeSourceAdvisor.
    */
   public TransactionAttributeSourceAdvisor() {
   }

   /**
    * Create a new TransactionAttributeSourceAdvisor.
    * @param interceptor the transaction interceptor to use for this advisor
    */
   public TransactionAttributeSourceAdvisor(TransactionInterceptor interceptor) {
      setTransactionInterceptor(interceptor);
   }


   /**
    * Set the transaction interceptor to use for this advisor.
    */
   public void setTransactionInterceptor(TransactionInterceptor interceptor) {
      this.transactionInterceptor = interceptor;
   }

   /**
    * Set the {@link ClassFilter} to use for this pointcut.
    * Default is {@link ClassFilter#TRUE}.
    */
   public void setClassFilter(ClassFilter classFilter) {
      this.pointcut.setClassFilter(classFilter);
   }


   @Override
   public Advice getAdvice() {
      return this.transactionInterceptor;
   }

   @Override
   public Pointcut getPointcut() {
      return this.pointcut;
   }

}
查看其对应的pointcut定义如下

abstract class TransactionAttributeSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

   @Override
   public boolean matches(Method method, Class<?> targetClass) {
      if (targetClass != null && TransactionalProxy.class.isAssignableFrom(targetClass)) {
         return false;
      }
      TransactionAttributeSource tas = getTransactionAttributeSource();
      return (tas == null || tas.getTransactionAttribute(method, targetClass) != null);
   }
很明显当实现TransactionalProxy接口的将不会开启事务【需要手动维护】

我们知道当定义spring事务的时候有个属性为proxy-target-class【默认为false则使用jdk代理 设置为true使用cglib代理】





