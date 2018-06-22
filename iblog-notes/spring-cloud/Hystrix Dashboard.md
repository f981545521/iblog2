## Hystrix Dashboard
Hystrix-dashboard是一款针对Hystrix进行实时监控的工具，
通过Hystrix Dashboard我们可以在直观地看到各Hystrix Command的请求响应时间, 请求成功率等数据。
但是只使用Hystrix Dashboard的话, 你只能看到单个应用内的服务信息, 这明显不够. 
我们需要一个工具能让我们汇总系统内多个服务的数据并显示到Hystrix Dashboard上, 这个工具就是Turbine.

### Hystrix Dashboard
#### 1、添加依赖
```
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
```

#### 2、启动类
```
    @SpringBootApplication
    @EnableDiscoveryClient
    @EnableFeignClients
    @EnableHystrixDashboard
    @EnableCircuitBreaker
    public class ConsumerApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(ConsumerApplication.class, args);
        }
    }
```
#### 3、测试
启动工程后访问 http://localhost:9001/hystrix


> 参考资料
> 
> [springcloud(五)：熔断监控Hystrix Dashboard和Turbine](http://www.ityouknow.com/springcloud/2017/05/18/hystrix-dashboard-turbine.html)








