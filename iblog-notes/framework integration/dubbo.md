## dubbo

#### 安装zookeeper
[zookeeper安装](zookeeper.md)
#### dubbo-admin 的下载，放到tomcat中启动即可
[dubbo-admin下载](https://pan.baidu.com/s/1i6uTLnJ)

> 安装完成后访问：`http://localhost:8080/dubbo-admin`
#### provider
1. provider.xml
```
    <!--定义了提供方应用信息，用于计算依赖关系；在 dubbo-admin 或 dubbo-monitor 会显示这个名字，方便辨识-->
    <dubbo:application name="demotest-provider" owner="programmer" organization="dubbox"/>
    <!--使用 zookeeper 注册中心暴露服务，注意要先开启 zookeeper-->
    <dubbo:registry address="zookeeper://localhost:2181"/>
    <!-- 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <!--使用 dubbo 协议实现定义好的 api.BossService 接口-->
    <dubbo:service interface="cn.acyou.iblog.service.BossService" ref="bossService" protocol="dubbo" />
    <!--具体实现该接口的 bean-->
    <bean id="bossService" class="cn.acyou.iblog.service.impl.BossServiceImpl"/>
```
> 需要注意的地方
1. 如果dubbo:registry 时配置了**group**，dubbo-admin不能自动发现服务；需要在dubbo-admin.xml中增加：
```
    <dubbo:registry address="${dubbo.registry.address}" group="dev" check="false" file="false" />
```
2. 如果存在多网卡的时候，dubbo不能确认您需要的网卡；需要在提供者增加host指定IP：
```
    <dubbo:protocol name="dubbo" port="20880" host="192.168.13.93" id="iblog-protocol" serialization="java"/>
```
2. 在applicationContext.xml中引入dubbo的配置文件即可
```
    <!-- Dubbo 服务 -->
    <import resource="classpath*:application-provider.xml"/>
```
#### consumer
```
    <dubbo:application name="demotest-consumer" owner="programmer" organization="dubbox"/>
    <!--向 zookeeper 订阅 provider 的地址，由 zookeeper 定时推送-->
    <dubbo:registry address="zookeeper://localhost:2181"/>
    <!-- 使用 dubbo 协议调用定义好的 api.BossService 接口 -->
    <!-- 服务消费方配置:生成远程服务代理，可以和本地bean一样使用bossService -->
    <dubbo:reference id="bossService" interface="cn.acyou.iblog.service.BossService"/>
	<!-- Spring注入dubbo中发现的Bean -->
    <dubbo:annotation package="cn.acyou.iblog.consumer"/>
```
#### 参考
[Dubbo入门](http://blog.csdn.net/noaman_wgs/article/details/70214612)

#### Telnet 命令参考手册
- telnet localhost 20880
- ls -l
- help



