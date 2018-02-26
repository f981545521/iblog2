### AOP面向切面编程

#### 切面：事务的横截面
特点：在不改变软件原有的功能情况下为软件插入（扩展）横切面功能；
#### 切面组件，通知：
- 目标方法：被AOP拦截的业务方法，称为目标方法；
- 切面方法的执行时机：就是在目标方法之前，之后执行。

	* @Before：切面方法执行之前
	* @After：切面方法执行之后。
	* @AfterReturning：
	* @AfterThrowing：在有异常的时候
```
    try{
             @Before
             //业务代码
             @AfterReturning
    }catch(e){
             @ AfterThrowing
    }finally{
             @After
    }
    @AfterReturning会确保在最后执行
```
#### Around通知：
环绕通知，要求方法：
 1. 必须有返回值;
 2. 必须有参数 ProceedingJoinPoint;
 3. 必须抛出异常;
 4. 需要在方法中调用 Object val=jp.proceed();
 5. 返回业务方法的返回值
### 切入点
用于定位AOP的切入位置：用于指定切入到具体的方法类。

1.Bean组件切入点
 - bean(userService)
 - bean(noteService)
 - bean(userService)||bean(noteService)||bean(noteBookService)
 - bean(*Service)
2. 类切入点
  - within(类名)           within(cn.tedu.note.service.Impl.UserServiceImpl)
  - within(类名)||within(类名)  
  - within(cn.tedu.note.*.impl.*ServiceImpl)
3. 方法切入点
  - @Around("bean(*Service)")

 
### 补充
- 代理模式：不改变原有功能，为其扩展新的功能；
- 通过反射，动态代理；就是AOP`java.lang.reflect.Proxy`
- AOP封装了动态代理功能，提供了更加简便的使用方式；
- AOP的底层使用了动态代理技术，

关键点：
1. Spring AOP利用了AspectJ AOP实现的；
2. AspectJ AOP的底层用了动态代理；
3. 其中：动态代理有两种：
    - JDK动态代理：目标方法必须有接口！
    - CGLib：目标方法没有接口的时候！

 
 
 
 
