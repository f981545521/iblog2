### 分布式/集群下Session跨域问题的解决方案

#### 第一种：利用负载均衡（nginx）的IP_Hash算法，实现用户请求总是由一台服务器处理
原理：粘性Session是指将用户锁定到某一个服务器上，比如上面说的例子，用户第一次请求时，负载均衡器将用户的请求转发到了A服务器上，如果负载均衡器设置了粘性Session的话，那么用户以后的每次请求都会转发到A服务器上，相当于把用户和A服务器粘到了一块，这就是粘性Session机制。
优点：简单，不需要对session做任何处理。

缺点：缺乏容错性，如果当前访问的服务器发生故障，用户被转移到第二个服务器上时，他的session信息都将失效。

实现方式：以Nginx为例，在upstream模块配置ip_hash属性即可实现粘性Session。
```
upstream mycluster{
    #这里添加的是上面启动好的两台Tomcat服务器
    ip_hash;#粘性Session
    server 192.168.22.229:8080 weight=1;
    server 192.168.22.230:8080 weight=1;
}
```
#### 第二种：服务器session复制

原理：任何一个服务器上的session发生改变（增删改），该节点会把这个 session的所有内容序列化，然后广播给所有其它节点，不管其他服务器需不需要session，以此来保证Session同步。

#### 第三种：使用第三方存储（redis）

使用分布式缓存方案比如memcached、redis，但是要求Memcached或Redis必须是集群。

### 使用SpringSession实现分布式Session共享的整合步骤

#### 1. Maven导入依赖
        <dependency>
            <groupId>org.springframework.session</groupId>
            <artifactId>spring-session-data-redis</artifactId>
            <version>1.2.0.RELEASE</version>
        </dependency>

#### 2. 配置Redis

#### applicationContext配置

    <!-- 将session放入redis -->
    <bean id="redisHttpSessionConfiguration"  class="org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration" >
        <property name="maxInactiveIntervalInSeconds" value="120" />
    </bean>

#### web.xml配置

    <!-- 分布式Session共享Filter -->
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>classpath:spring/spring-*.xml</param-value>
    </context-param>
    <filter>
        <filter-name>springSessionRepositoryFilter</filter-name>
        <filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
    </filter>
    <filter-mapping>
        <filter-name>springSessionRepositoryFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>







