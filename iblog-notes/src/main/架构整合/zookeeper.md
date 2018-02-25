## zookeeper_study


### zookeeper安装和使用 windows环境
1. download ...

https://mirrors.cnnic.cn/apache/zookeeper/zookeeper-3.4.10/

2.  修改zoo_sample.cfg 文件名(D:\soft\zookeeper-3.4.8\conf) 为 zoo.cfg
主要修改一下日志位置，具体配置文件如下：

```
	# The number of milliseconds of each tick  
	tickTime=2000  
	# The number of ticks that the initial   
	# synchronization phase can take  
	initLimit=10  
	# The number of ticks that can pass between   
	# sending a request and getting an acknowledgement  
	syncLimit=5  
	# the directory where the snapshot is stored.  
	# do not use /tmp for storage, /tmp here is just   
	# example sakes.  
	dataDir=D:\\zookeeper\\data  
	dataLogDir=D:\\zookeeper\\log  
	# the port at which the clients will connect  
	clientPort=2181  
	# the maximum number of client connections.  
	# increase this if you need to handle more clients  
	#maxClientCnxns=60  
	#  
	# Be sure to read the maintenance section of the   
	# administrator guide before turning on autopurge.  
	#  
	# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance  
	#  
	# The number of snapshots to retain in dataDir  
	#autopurge.snapRetainCount=3  
	# Purge task interval in hours  
	# Set to "0" to disable auto purge feature  
	#autopurge.purgeInterval=1  
```
配置文件简单解析
1、tickTime：这个时间是作为 Zookeeper 服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每个 tickTime 时间就会发送一个心跳。
2、dataDir：顾名思义就是 Zookeeper 保存数据的目录，默认情况下，Zookeeper 将写数据的日志文件也保存在这个目录里。
3、dataLogDir：顾名思义就是 Zookeeper 保存日志文件的目录
4、clientPort：这个端口就是客户端连接 Zookeeper 服务器的端口，Zookeeper 会监听这个端口，接受客户端的访问请求。

3. start ...

进入到bin目录，并且启动zkServer.cmd，这个脚本中会启动一个java进程
PS C:\WINDOWS\system32> jps -l -v
8740 org.apache.zookeeper.server.quorum.QuorumPeerMain......
PS F:\developer\zookeeper-3.4.10\bin> .\zkCli.cmd 127.0.0.1:2181
Connecting to localhost:2181
.....






















