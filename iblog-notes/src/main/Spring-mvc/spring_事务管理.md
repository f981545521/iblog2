### Spring事务管理

#### 只读属性
对于单纯读取数据库操作，可以设置readOnly=true，可以提高数据的读取效率；`@Transactional(readOnly=true)``

#### 事务传播(propagation)属性
重点掌握 Propagation.REQUIRED

1. @Transactional(propagation=Propagation.REQUIRED)
    * 需要事务, 如果没有事务创建新事务, 如果当前有事务参与当前事务
1. @Transactional(propagation=Propagation.MANDATORY)
    * 必须有事务, 如果当前没有事务就抛异常
1. @Transactional(propagation=Propagation.NEVER)
    * 绝不, 绝对不能有事务, 如果在事务中调用则抛出异常
1. @Transactional(propagation=Propagation.NESTED)
    * 嵌套, 必须被嵌套到其他事务中
1. @Transactional(propagation=Propagation.NOT_SUPPORTED)
    * 不支持事务
1. @Transactional(propagation=Propagation.SUPPORTS)
    * 支持事务, 如果没有事务也不会创建新事务
1. @Transactional(propagation=Propagation.REQUIRES_NEW)
    * 必须是新事务, 如果有当前事务, 挂起当前事务并且开启新事务.
#### 事务隔离属性
隔离级别：用来解决并发问题
一共有4种, 一般采用 `@Transactional(isolation=Isolation.READ_COMMITTED)` 级别, 是并发性能和安全性折中的选择. 是大多数软件项目采用的隔离级别.

1.      串行化；         最安全，性能差
2.      可以重复读取；
3.      读取提交以后的数据；默认隔离级别
4.      读取未提交的数据；     不安全，性能好

#### 声明式事务
- 就是所有业务方法使用@Transactional注解，即可
- 如果是只读方法，建议增加readOnly=true
- Spring声明式事务的底层的AOP；AOP使用动态代理；动态代理使用反射
- Spring 声明式事务是利用AOP技术实现.




