### Quarz 定时任务
#### Quarz整合Spring
1. **pom.xml**
```

        <!-- quartz 的jar -->
        <dependency>
            <groupId>org.opensymphony.quartz</groupId>
            <artifactId>quartz-all</artifactId>
            <version>1.6.1</version>
        </dependency>
        <dependency>
            <groupId>commons-collections</groupId>
            <artifactId>commons-collections</artifactId>
            <version>3.2.1</version>
        </dependency>
        <!-- 此外还需要Spring 的支持 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
        </dependency>
```
2. **执行方法**
```
    public class MonitorJob {
        private Logger log = LoggerFactory.getLogger(this.getClass());
        /**
         * 第一个任务
         *
         */
        public void firstJob(){
            log.info("first QuarzJob"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );
        }
    }
```
3. **Spring配置**
```
    <bean id="monitorJob" class="cn.acyou.iblog.quarz.MonitorJob"/>
    <bean id="firstJobTask"
          class="org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean">
        <property name="targetObject" ref="monitorJob"/>
        <property name="targetMethod" value="firstJob"/>
    </bean>

    <bean id="firstJobTaskTrigger"
          class="org.springframework.scheduling.quartz.CronTriggerBean">
        <property name="jobDetail" ref="firstJobTask"/>
        <property name="cronExpression" value="*/5 * * * * ?"/>
    </bean>

    <bean id="startQuertz" autowire="no"
          class="org.springframework.scheduling.quartz.SchedulerFactoryBean">
        <property name="triggers">
            <list>
                <ref local="firstJobTaskTrigger"/>
            </list>
        </property>
    </bean>
```
4. Spring的配置文件中
    <!-- Quarz定时任务 -->
    <import resource="classpath*:applicationContext-quarz.xml"/>

#### 扩展：Spring的@Scheduled
1. 编写任务方法，并将Bean注入到Spring中
```
    @Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
    public void myTest() {
        System.out.println("进入测试");
    }
```
2. Spring的配置文件中
```
    <!-- Spring-Scheduled定时任务 -->
    <task:executor id="executor" pool-size="5" />
    <task:scheduler id="scheduler" pool-size="10" />
    <task:annotation-driven executor="executor" scheduler="scheduler" />
```
#### 附录：Cron表达式的格式：秒 分 时 日 月 周 年(可选)。
       字段名                 允许的值                        允许的特殊字符
       秒                     0-59                               , - * /
       分                     0-59                               , - * /
       小时                   0-23                               , - * /
       日                     1-31                               , - * ? / L W C
       月                     1-12 or JAN-DEC                    , - * /
       周几                   1-7 or SUN-SAT                     , - * ? / L C #
       年 (可选字段)          empty, 1970-2099                    , - * /

 - “?”字符：表示不确定的值
 - “,”字符：指定数个值
 - “-”字符：指定一个值的范围
 - “/”字符：指定一个值的增加幅度。n/m表示从n开始，每次增加m
 - “L”字符：用在日表示一个月中的最后一天，用在周表示该月最后一个星期X
 - “W”字符：指定离给定日期最近的工作日(周一到周五)
 - “#”字符：表示该月第几个周X。6#3表示该月第3个周五

#### Cron表达式范例：
 - 每隔5秒执行一次：*/5 * * * * ?
 - 每隔1分钟执行一次：0 */1 * * * ?
 - 每天23点执行一次：0 0 23 * * ?
 - 每天凌晨1点执行一次：0 0 1 * * ?
 - 每月1号凌晨1点执行一次：0 0 1 1 * ?
 - 每月最后一天23点执行一次：0 0 23 L * ?
 - 每周星期天凌晨1点实行一次：0 0 1 ? * L
 - 在26分、29分、33分执行一次：0 26,29,33 * * * ?
 - 每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?