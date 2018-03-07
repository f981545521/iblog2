背景
上篇我们说明了事务实现基本要素aop等等Spring的事务实现一

继续描述一下事务引入的几种方式

方式
注解
spring3.1之后引入了新的注解EnableTransactionManagement

为了不维护太多的xml Spring从3.1版本组件增加java的配置类

而EnableTransactionManagement就是其中一个开启事务的注解

SpringBoot是注解的集大成者

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TransactionManagementConfigurationSelector.class)
public @interface EnableTransactionManagement {

   /**
    * Indicate whether subclass-based (CGLIB) proxies are to be created ({@code true}) as
    * opposed to standard Java interface-based proxies ({@code false}). The default is
    * {@code false}. <strong>Applicable only if {@link #mode()} is set to
    * {@link AdviceMode#PROXY}</strong>.
    * <p>Note that setting this attribute to {@code true} will affect <em>all</em>
    * Spring-managed beans requiring proxying, not just those marked with
    * {@code @Transactional}. For example, other beans marked with Spring's
    * {@code @Async} annotation will be upgraded to subclass proxying at the same
    * time. This approach has no negative impact in practice unless one is explicitly
    * expecting one type of proxy vs another, e.g. in tests.
    */
   boolean proxyTargetClass() default false;

   /**
    * Indicate how transactional advice should be applied. The default is
    * {@link AdviceMode#PROXY}.
    * @see AdviceMode
    */
   AdviceMode mode() default AdviceMode.PROXY;

   /**
    * Indicate the ordering of the execution of the transaction advisor
    * when multiple advices are applied at a specific joinpoint.
    * The default is {@link Ordered#LOWEST_PRECEDENCE}.
    */
   int order() default Ordered.LOWEST_PRECEDENCE;

}

一旦使用了该注解很明显会引入TransactionManagementConfigurationSelector

public class TransactionManagementConfigurationSelector extends AdviceModeImportSelector<EnableTransactionManagement> {

   /**
    * {@inheritDoc}
    * @return {@link ProxyTransactionManagementConfiguration} or
    * {@code AspectJTransactionManagementConfiguration} for {@code PROXY} and
    * {@code ASPECTJ} values of {@link EnableTransactionManagement#mode()}, respectively
    */
   @Override
   protected String[] selectImports(AdviceMode adviceMode) {
      switch (adviceMode) {
         case PROXY:
            return new String[] {AutoProxyRegistrar.class.getName(), ProxyTransactionManagementConfiguration.class.getName()};
         case ASPECTJ:
            return new String[] {TransactionManagementConfigUtils.TRANSACTION_ASPECT_CONFIGURATION_CLASS_NAME};
         default:
            return null;
      }
   }

}

通常来说我们都是使用proxy 而使用aspectj相对来说比较麻烦需要在编译期支持

那么就会引入ProxyTransactionManagementConfiguration

@Configuration
public class ProxyTransactionManagementConfiguration extends AbstractTransactionManagementConfiguration {

   @Bean(name=TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME)
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor() {
      BeanFactoryTransactionAttributeSourceAdvisor advisor = new BeanFactoryTransactionAttributeSourceAdvisor();
      advisor.setTransactionAttributeSource(transactionAttributeSource());
      advisor.setAdvice(transactionInterceptor());
      advisor.setOrder(this.enableTx.<Integer>getNumber("order"));
      return advisor;
   }

   @Bean
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public TransactionAttributeSource transactionAttributeSource() {
      return new AnnotationTransactionAttributeSource();
   }

   @Bean
   @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
   public TransactionInterceptor transactionInterceptor() {
      TransactionInterceptor interceptor = new TransactionInterceptor();
      interceptor.setTransactionAttributeSource(transactionAttributeSource());
      if (this.txManager != null) {
         interceptor.setTransactionManager(this.txManager);
      }
      return interceptor;
   }

}
从这边可以知道advisor使用的是BeanFactoryTransactionAttributeSourceAdvisor===》this.enableTx.<Integer>getNumber("order") 这个enableTX是从EnableTransactionManagement读取的order 默认为最低顺序？【思考是为什么】

默认情况下使用的是注解支持的attributeSource 事务注解与tx:attributes共存

同时我们使用真正的业务实现在此【事务切面】TransactionInterceptor

advisor可以告诉我们是如何进行拦截的

public class BeanFactoryTransactionAttributeSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor {

   private TransactionAttributeSource transactionAttributeSource;

   private final TransactionAttributeSourcePointcut pointcut = new TransactionAttributeSourcePointcut() {
      @Override
      protected TransactionAttributeSource getTransactionAttributeSource() {
         return transactionAttributeSource;
      }
   };


   /**
    * Set the transaction attribute source which is used to find transaction
    * attributes. This should usually be identical to the source reference
    * set on the transaction interceptor itself.
    * @see TransactionInterceptor#setTransactionAttributeSource
    */
   public void setTransactionAttributeSource(TransactionAttributeSource transactionAttributeSource) {
      this.transactionAttributeSource = transactionAttributeSource;
   }

   /**
    * Set the {@link ClassFilter} to use for this pointcut.
    * Default is {@link ClassFilter#TRUE}.
    */
   public void setClassFilter(ClassFilter classFilter) {
      this.pointcut.setClassFilter(classFilter);
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

}

这就是我们使用的pointCut可以看到这边的attributeSource是从传入的===》此处是AnnotationTransactionAttributeSource

XML
虽然Spring提供了注解形式开启事务的方式，但是还有好多小伙伴会使用xml方式

<aop:config proxy-target-class="true">
    <aop:pointcut id="serviceMethod"
                  expression=" execution(public *  com.air.tqb.service..*(..)) and !execution(public * com.air.tqb.service.report..*(..)) and !execution(public * com.air.tqb.service.saiku..*(..))"/>
    <aop:advisor order="1" pointcut-ref="serviceMethod" advice-ref="txAdvice"/>
</aop:config>
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <tx:attributes>
        <tx:method name="addBranch" read-only="false" rollback-for="java.lang.Exception"
                   timeout="100"/>
        <tx:method name="load*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="find*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="is*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="has*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="check*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="get*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="select*" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="list*" read-only="true" propagation="SUPPORTS"
                   timeout="50"/>
        <tx:method name="loginCheck" read-only="true" propagation="SUPPORTS"
                   timeout="20"/>
        <tx:method name="selectDiff*" read-only="true" propagation="SUPPORTS"
                   timeout="120"/>
        <tx:method name="*" propagation="REQUIRED" rollback-for="java.lang.Exception"
                   timeout="50"/>
    </tx:attributes>
</tx:advice>
这是一种方式用来提供NameMatch

还有一种注解形式的事务注解与tx:attributes共存【这个注解是指具体使用什么事务】

通常来说我们使用

tx:advice

但是使用advice是远远不够的 这个advice需要和pointCut集合起来组成advisor

/**
 * Create a {@link RootBeanDefinition} for the advisor described in the supplied. Does <strong>not</strong>
 * parse any associated '{@code pointcut}' or '{@code pointcut-ref}' attributes.
 */
private AbstractBeanDefinition createAdvisorBeanDefinition(Element advisorElement, ParserContext parserContext) {
   RootBeanDefinition advisorDefinition = new RootBeanDefinition(DefaultBeanFactoryPointcutAdvisor.class);
   advisorDefinition.setSource(parserContext.extractSource(advisorElement));

   String adviceRef = advisorElement.getAttribute(ADVICE_REF);
   if (!StringUtils.hasText(adviceRef)) {
      parserContext.getReaderContext().error(
            "'advice-ref' attribute contains empty value.", advisorElement, this.parseState.snapshot());
   }
   else {
      advisorDefinition.getPropertyValues().add(
            ADVICE_BEAN_NAME, new RuntimeBeanNameReference(adviceRef));
   }

   if (advisorElement.hasAttribute(ORDER_PROPERTY)) {
      advisorDefinition.getPropertyValues().add(
            ORDER_PROPERTY, advisorElement.getAttribute(ORDER_PROPERTY));
   }

   return advisorDefinition;
}
很明显 我们使用的就是DefaultBeanFactoryPointcutAdvisor

public class DefaultBeanFactoryPointcutAdvisor extends AbstractBeanFactoryPointcutAdvisor {

   private Pointcut pointcut = Pointcut.TRUE;


   /**
    * Specify the pointcut targeting the advice.
    * <p>Default is {@code Pointcut.TRUE}.
    * @see #setAdviceBeanName
    */
   public void setPointcut(Pointcut pointcut) {
      this.pointcut = (pointcut != null ? pointcut : Pointcut.TRUE);
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }


   @Override
   public String toString() {
      return getClass().getName() + ": pointcut [" + getPointcut() + "]; advice bean '" + getAdviceBeanName() + "'";
   }

}



此处可以看到如果不设置PointCut那么使用的就是PointCut.True

class TruePointcut implements Pointcut, Serializable {

   public static final TruePointcut INSTANCE = new TruePointcut();

   /**
    * Enforce Singleton pattern.
    */
   private TruePointcut() {
   }

   public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
   }

   public MethodMatcher getMethodMatcher() {
      return MethodMatcher.TRUE;
   }

   /**
    * Required to support serialization. Replaces with canonical
    * instance on deserialization, protecting Singleton pattern.
    * Alternative to overriding {@code equals()}.
    */
   private Object readResolve() {
      return INSTANCE;
   }

   @Override
   public String toString() {
      return "Pointcut.TRUE";
   }

}

换言之将所有情况下都会proxy











