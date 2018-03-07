背景
各位看官看一下 读写分离实现

除此之外我们来简述一下spring提供的一些事务传播

propagation_requierd：如果当前没有事务，就新建一个事务，如果已存在一个事务中，加入到这个事务中，这是最常见的选择。
propagation_supports：支持当前事务，如果没有当前事务，就以非事务方法执行。
propagation_mandatory：使用当前事务，如果没有当前事务，就抛出异常。
propagation_required_new：新建事务，如果当前存在事务，把当前事务挂起。
propagation_not_supported：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
propagation_never：以非事务方式执行操作，如果当前事务存在则抛出异常。
propagation_nested：如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与propagation_required类似的操作
我们最常并且默认使用的是propagation_requierd



为何读写分离要和传播行为一起提到呢?静待下文分解

我们默认配置了service级别的事务attribute

<tx:attributes>
    <tx:method name="getBillNo" read-only="false" propagation="REQUIRES_NEW"
               timeout="20"/>
    <tx:method name="addBranch" read-only="false" rollback-for="java.lang.Exception"
               timeout="100"/>
    <tx:method name="load*" read-only="true" propagation="SUPPORTS"
               timeout="20"/>
    <tx:method name="find*" read-only="true" propagation="SUPPORTS"
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
    <tx:method name="importBatch*" propagation="REQUIRED" rollback-for="java.lang.Exception"
               timeout="180"/>
    <tx:method name="fastImport*" propagation="REQUIRED" rollback-for="java.lang.Exception"
               timeout="180"/>
    <tx:method name="createMonitor*" propagation="REQUIRED" rollback-for="java.lang.Exception"
               timeout="500"/>
    <tx:method name="*" propagation="REQUIRED" rollback-for="java.lang.Exception"
               timeout="50"/>
很明显可以看到除了极少部分的requires_new 大部分特殊设置的方法均为Supports 其他的方法均为Required

当然此时事务注解和方法名称配置不能共存 解决方法事务注解与tx:attributes共存

问题
2018-02-23 09:37:41,640 [ERROR] [getPrintInfo-45] c.a.t.s.a.ExceptionHandlerAspect:?
### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: INSERT command denied to user 'readonly'@'10.25.24.205' for table 'tm_print_count'
### The error may involve com.air.tqb.mapper.print.PrintCountMapper.addPrintCount-Inline
### The error occurred while setting parameters
### SQL: insert into tm_print_count(pk_id,id_bill,bill_no,bill_type,print_count,creationtime,modifiedtime,id_own_org)         VALUES (CAST(? as unsigned ),?,?,?,1,now(),now(),CAST(? as unsigned         ))
### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: INSERT command denied to user 'readonly'@'10.25.24.205' for table 'tm_print_count'
; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: INSERT command denied to user 'readonly'@'10.25.24.205' for table 'tm_print_count'^M
IP:125.80.227.73^M
IdOwnOrg:10545724654275035769^M
UserID:10545724654275044496^M
Type:WEB^M
Action:/kzf6/maintain/printMaintain/10545724654284677049.do^M
Args:[{"billId":"10545724654284677049","billNo":"GD20180223003","billType":"GD","idOwnOrg":"10545724654275035769","pkId":"5285653139687473168","printCount":1}]
org.springframework.jdbc.BadSqlGrammarException:
### Error updating database.  Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: INSERT command denied to user 'readonly'@'10.25.24.205' for table 'tm_print_count'
### The error may involve com.air.tqb.mapper.print.PrintCountMapper.addPrintCount-Inline
### The error occurred while setting parameters
### SQL: insert into tm_print_count(pk_id,id_bill,bill_no,bill_type,print_count,creationtime,modifiedtime,id_own_org)         VALUES (CAST(? as unsigned ),?,?,?,1,now(),now(),CAST(? as unsigned         ))
### Cause: com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: INSERT command denied to user 'readonly'@'10.25.24.205' for table 'tm_print_count'
; bad SQL grammar []; nested exception is com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: INSERT command denied to user 'readonly'@'10.25.24.205' for table 'tm_print_count'
        at org.springframework.jdbc.support.SQLExceptionSubclassTranslator.doTranslate(SQLExceptionSubclassTranslator.java:95)
        at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:72)
        at org.springframework.jdbc.support.AbstractFallbackSQLExceptionTranslator.translate(AbstractFallbackSQLExceptionTranslator.java:80)
        at org.mybatis.spring.MyBatisExceptionTranslator.translateExceptionIfPossible(MyBatisExceptionTranslator.java:74)
        at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:421)
        at com.sun.proxy.$Proxy50.insert(Unknown Source)
        at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:254)
        at org.apache.ibatis.binding.MapperMethod.execute(MapperMethod.java:52)
        at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:53)
        at com.sun.proxy.$Proxy122.addPrintCount(Unknown Source)
        at com.air.tqb.service.print.impl.PrintCountServiceImpl.addPrintCount(PrintCountServiceImpl.java:22)
        at com.air.tqb.service.print.impl.PrintCountServiceImpl.getPrintCountIfNull(PrintCountServiceImpl.java:38)
        at com.air.tqb.service.print.impl.PrintCountServiceImpl$$FastClassBySpringCGLIB$$9e79b679.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:700)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
        at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:91)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:80)
        at com.air.tqb.spring.aop.ExceptionHandlerAspect.handleException(ExceptionHandlerAspect.java:48)
        at sun.reflect.GeneratedMethodAccessor219.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:606)
        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:621)
        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:610)
        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:65)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:633)
        at com.air.tqb.service.print.impl.PrintCountServiceImpl$$EnhancerBySpringCGLIB$$c77e82b5.getPrintCountIfNull(<generated>)
        at com.air.tqb.service.maintain.impl.MaintainServiceImpl.getPrintInfo(MaintainServiceImpl.java:847)
        at com.air.tqb.service.maintain.impl.MaintainServiceImpl$$FastClassBySpringCGLIB$$10ec1b0c.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:700)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
        at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:96)
        at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:260)
        at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:94)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:91)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:80)
        at com.air.tqb.spring.aop.ExceptionHandlerAspect.handleException(ExceptionHandlerAspect.java:48)
        at sun.reflect.GeneratedMethodAccessor219.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:606)
        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:621)
        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:610)
        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:65)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:172)
        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:633)
        at com.air.tqb.service.maintain.impl.MaintainServiceImpl$$EnhancerBySpringCGLIB$$9948a15d.getPrintInfo(<generated>)
        at com.air.tqb.control.MaintainController.printMaintain(MaintainController.java:573)
        at com.air.tqb.control.MaintainController$$FastClassBySpringCGLIB$$99d77692.invoke(<generated>)
        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:204)
        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:700)
        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
        at org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)
        at org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)
        at org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)

小伙伴表示此处addPrintCount明显没有加任何标签

@Override
public void addPrintCount(TmPrintCount tmPrintCount) {
    printCountMapper.addPrintCount(tmPrintCount);
}
那么怎么解释出现了readOnly呢？

释疑
上面我们提到了一个关键  事务的传播

我们知道spring的事务的实现是通过ThreadLocal来实现的 为何需要通过ThreadLocal来实现呢？

当我们享受着Spring带来的事务管理的同时应该细想 为何spring在不同的方法中可以提供传播特性

我们知道当我们在mysql查询中当设置commit为false时此时将不会默认提交该事务【提交事务是当手动调用commit才会提交】

那么我们使用到事务的时候spring也是如此处理

//---------------------------------------------------------------------
// Implementation of PlatformTransactionManager
//---------------------------------------------------------------------

/**
 * This implementation handles propagation behavior. Delegates to
 * {@code doGetTransaction}, {@code isExistingTransaction}
 * and {@code doBegin}.
 * @see #doGetTransaction
 * @see #isExistingTransaction
 * @see #doBegin
 */
public final TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
   Object transaction = doGetTransaction();

   // Cache debug flag to avoid repeated checks.
   boolean debugEnabled = logger.isDebugEnabled();

   if (definition == null) {
      // Use defaults if no transaction definition given.
      definition = new DefaultTransactionDefinition();
   }

   if (isExistingTransaction(transaction)) {
      // Existing transaction found -> check propagation behavior to find out how to behave.
      return handleExistingTransaction(definition, transaction, debugEnabled);
   }

   // Check definition settings for new transaction.
   if (definition.getTimeout() < TransactionDefinition.TIMEOUT_DEFAULT) {
      throw new InvalidTimeoutException("Invalid transaction timeout", definition.getTimeout());
   }

   // No existing transaction found -> check propagation behavior to find out how to proceed.
   if (definition.getPropagationBehavior() == TransactionDefinition.PROPAGATION_MANDATORY) {
      throw new IllegalTransactionStateException(
            "No existing transaction found for transaction marked with propagation 'mandatory'");
   }
   else if (definition.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRED ||
         definition.getPropagationBehavior() == TransactionDefinition.PROPAGATION_REQUIRES_NEW ||
      definition.getPropagationBehavior() == TransactionDefinition.PROPAGATION_NESTED) {
      SuspendedResourcesHolder suspendedResources = suspend(null);
      if (debugEnabled) {
         logger.debug("Creating new transaction with name [" + definition.getName() + "]: " + definition);
      }
      try {
         boolean newSynchronization = (getTransactionSynchronization() != SYNCHRONIZATION_NEVER);
         DefaultTransactionStatus status = newTransactionStatus(
               definition, transaction, true, newSynchronization, debugEnabled, suspendedResources);
         doBegin(transaction, definition);
         prepareSynchronization(status, definition);
         return status;
      }
      catch (RuntimeException ex) {
         resume(null, suspendedResources);
         throw ex;
      }
      catch (Error err) {
         resume(null, suspendedResources);
         throw err;
      }
   }
   else {
      // Create "empty" transaction: no actual transaction, but potentially synchronization.
      boolean newSynchronization = (getTransactionSynchronization() == SYNCHRONIZATION_ALWAYS);
      return prepareTransactionStatus(definition, null, true, newSynchronization, debugEnabled, null);
   }
}
当不存在事务的情况
PROPAGATION_REQUIRED
PROPAGATION_REQUIRES_NEW PROPAGATION_NESTED的时候默认会创建事务

其他则创建empty事务
我们仅仅分析这种简单的情况

注意Transaction不存在和Synchronization不存在是两个概念。

在事务的描述中我们通常关注Transaction

可以看到无论是否存在和传播类型都将会创建transaction 但是对于supports或者no_support等来说其不存在真实的事务【或者说自动提交】

但是依然会创建Synchronization【Synchronization用来描述事务的同步状态 其中放置了一些事务的状态 是否只读等等】

我们可以认为在service无嵌套调用时【既无Transaction也无Synchronization】

此时对于一个supports传播行为来说将会创建一个空的事务【即DefaultTransactionStatus的事务为空 TransactionSynchronizationManager#isActualTransactionActive也为空】但是仍然会有Synchronizationd的创建 参考 https://stackoverflow.com/questions/18771296/spring-transactions-transactionsynchronizationmanager-isactualtransactionactive

我们可以看一下具体场景下何时会设置成newSynchronization

/**
 * Create a rae TransactionStatus instance for the given arguments.
 */
protected DefaultTransactionStatus newTransactionStatus(
      TransactionDefinition definition, Object transaction, boolean newTransaction,
      boolean newSynchronization, boolean debug, Object suspendedResources) {

   boolean actualNewSynchronization = newSynchronization &&
         !TransactionSynchronizationManager.isSynchronizationActive();
   return new DefaultTransactionStatus(
         transaction, newTransaction, actualNewSynchronization,
         definition.isReadOnly(), debug, suspendedResources);
}
可以看到当前service无嵌套的场景下TransactionSynchronizationManager.isSynchronizationActive为false 因此当嵌套调用service的情况下

actualNewSynchronization必然为false 这样就不会创建新的事务同步
可以看到事务同步新标志存在的时候再service收尾的时候如下

/**
 * Trigger {@code afterCompletion} callbacks.
 * @param status object representing the transaction
 * @param completionStatus completion status according to TransactionSynchronization constants
 */
private void triggerAfterCompletion(DefaultTransactionStatus status, int completionStatus) {
   if (status.isNewSynchronization()) {
      List<TransactionSynchronization> synchronizations = TransactionSynchronizationManager.getSynchronizations();
      if (!status.hasTransaction() || status.isNewTransaction()) {
         if (status.isDebug()) {
            logger.trace("Triggering afterCompletion synchronization");
         }
         // No transaction or new transaction for the current scope ->
         // invoke the afterCompletion callbacks immediately
         invokeAfterCompletion(synchronizations, completionStatus);
      }
      else if (!synchronizations.isEmpty()) {
         // Existing transaction that we participate in, controlled outside
         // of the scope of this Spring transaction manager -> try to register
         // an afterCompletion callback with the existing (JTA) transaction.
         registerAfterCompletionWithExistingTransaction(status.getTransaction(), synchronizations);
      }
   }
}

我们继续分析当为supports的场景下 spring不需要执行rollback和commit===》因此也将无法执行到triggerAfterComplete等回调

因此也就无法执行到如下语句

@Override
protected void doCleanupAfterCompletion(Object transaction) {
   DataSourceTransactionObject txObject = (DataSourceTransactionObject) transaction;

   // Remove the connection holder from the thread, if exposed.
   if (txObject.isNewConnectionHolder()) {
      TransactionSynchronizationManager.unbindResource(this.dataSource);
   }

   // Reset connection.
   Connection con = txObject.getConnectionHolder().getConnection();
   try {
      if (txObject.isMustRestoreAutoCommit()) {
         con.setAutoCommit(true);
      }
      DataSourceUtils.resetConnectionAfterTransaction(con, txObject.getPreviousIsolationLevel());
   }
   catch (Throwable ex) {
      logger.debug("Could not reset JDBC Connection after transaction", ex);
   }

   if (txObject.isNewConnectionHolder()) {
      if (logger.isDebugEnabled()) {
         logger.debug("Releasing JDBC Connection [" + con + "] after transaction");
      }
      DataSourceUtils.releaseConnection(con, this.dataSource);
   }

   txObject.getConnectionHolder().clear();
}

可以看到这里有一个关键 当isConnectionHolder为true的时候将会执行unbind===》对应的dataSource作为key存储的实质上是connectionHolder 也就是connection

因此在多次supprots的之后其实connection并未发生变化

设想场景如下：

method	dataSource	routingkey	connection
getXXX	RoutingDataSource	readOnly	readOnly
因此如果当第一次调用getConnection如果获取了一个readOnly的连接 那么该连接将会存放在connectionHolder中

那么比如如下方法第一个调用时

@Cacheable(value = "other", key = "#idOwnOrg+'Staff'")
@DataSource(DataSourceType.READONLY)
@Override public List<TmEmployee> getStaffList(String idOwnOrg) {
   return this.staffMapper.getStaffList(idOwnOrg);
}

不考虑缓存的场景下 此时connection为readOnly

那么当调用到如下方法时

public TmPrintCount getPrintCountIfNull(TmPrintCount printCount) {
    String idOwnOrg = WxbStatic.getOrg();
    TmPrintCount tmPrintCount = this.getPrintCountByBillId(printCount);
    if (null == tmPrintCount){
        printCount.setPkId(baseService.getUUid());
        printCount.setPrintCount(1);
        printCount.setIdOwnOrg(idOwnOrg);
        this.addPrintCount(printCount);
        return printCount;
    }
    return tmPrintCount;
}
很明显get方法此时仍然是supports 那么此时获取到的连接仍然是readOnly 自然会报错

但是由于第一个方法存在缓存===》当缓存生效的时候此时就不需要执行到getConnection 因此也就可以成功出现



解决方案
方案有多种 可以考虑将getPringCountIfNull修改名称为addprintCountIfNull

小伙伴也可以考虑其他的方案~