### 读取配置文件存在的问题

#### 关于<context:property-placeholder>的一个有趣现象
描述：将redis.properties存放到common工程下后，读取配置文件报错如下：
```
Could not resolve placeholder 'master.jdbc.url' in string value "${master.jdbc.url}";
nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder 'master.jdbc.url' in string value "${master.jdbc.url}"
```
原因：在web工程中已经读取了一些配置文件
```
    <!--读取properties配置文件-->
    <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="locations">
            <list>
                <value>classpath*:common.properties</value>
                <value>classpath*:conf/db.properties</value>
            </list>
        </property>
    </bean>
```
> 即Spring容器仅允许最多定义一个PropertyPlaceholderConfigurer(或<context:property-placeholder/>)，其余的会被Spring忽略掉。
> 拿上来的例子来说，如果A和B模块是单独运行的，由于Spring容器都只有一个PropertyPlaceholderConfigurer，因此属性文件会被正常加载并替换掉。如果A和B两模块集成后运行，Spring容器中就有两个PropertyPlaceholderConfigurer Bean了，这时就看谁先谁后了， 先的保留，后的忽略！因此，**只加载到了一个属性文件**，因而造成无法正确进行属性替换的问题。