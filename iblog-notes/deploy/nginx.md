## nginx

### nginx安装
#### 1. 下载

进入http://nginx.org/en/download.html 下载nginx1.8.0版本（当前最新稳定版本）。

### 2.	安装
#### nginx安装环境
> nginx是C语言开发，建议在linux上运行，本教程使用Centos6.5作为安装环境。

- gcc
	安装nginx需要先将官网下载的源码进行编译，编译依赖gcc环境，如果没有gcc环境，需要安装gcc：`yum install gcc-c++`
- PCRE
	PCRE(Perl Compatible Regular Expressions)是一个Perl库，包括 perl 兼容的正则表达式库。nginx的http模块使用pcre来解析正则表达式，所以需要在linux上安装pcre库。`yum install -y pcre pcre-devel`
> 注：pcre-devel是使用pcre开发的一个二次开发库。nginx也需要此库。
- zlib
	zlib库提供了很多种压缩和解压缩的方式，nginx使用zlib对http包的内容进行gzip，所以需要在linux上安装zlib库。`yum install -y zlib zlib-devel`
- openssl
	OpenSSL 是一个强大的安全套接字层密码库，囊括主要的密码算法、常用的密钥和证书封装管理功能及SSL协议，并提供丰富的应用程序供测试或其它目的使用。
	nginx不仅支持http协议，还支持https（即在ssl协议上传输http），所以需要在linux安装openssl库。`yum install -y openssl openssl-devel`

#### 编译安装
将nginx-1.8.0.tar.gz拷贝至linux服务器。
解压：
```
 tar -zxvf nginx-1.8.0.tar.gz
 cd nginx-1.8.0
```
##### 1. configure
查询详细参数`./configure --help`

参数设置如下：
```
./configure \
--prefix=/usr/local/nginx \
--pid-path=/var/run/nginx/nginx.pid \
--lock-path=/var/lock/nginx.lock \
--error-log-path=/var/log/nginx/error.log \
--http-log-path=/var/log/nginx/access.log \
--with-http_gzip_static_module \
--http-client-body-temp-path=/var/temp/nginx/client \
--http-proxy-temp-path=/var/temp/nginx/proxy \
--http-fastcgi-temp-path=/var/temp/nginx/fastcgi \
--http-uwsgi-temp-path=/var/temp/nginx/uwsgi \
--http-scgi-temp-path=/var/temp/nginx/scgi
```

>注意：上边将临时文件目录指定为/var/temp/nginx，需要在/var下创建temp及nginx目录

##### 2. 编译安装
```
make
make  install
```
##### 3. 启动nginx
```
cd /usr/local/nginx/sbin/
./nginx
```

> 注意：执行./nginx启动nginx，这里可以-c指定加载的nginx配置文件，如：`./nginx -c /usr/local/nginx/conf/nginx.conf`
如果不指定-c，nginx在启动时默认加载conf/nginx.conf文件，此文件的地址也可以在编译安装nginx时指定./configure的参数（--conf-path= 指向配置文件（nginx.conf））

##### 4. 停止nginx

- 方式1，快速停止：
```
cd /usr/local/nginx/sbin
./nginx -s stop
```
此方式相当于先查出nginx进程id再使用kill命令强制杀掉进程。

- 方式2，完整停止(建议使用)：
```
cd /usr/local/nginx/sbin
./nginx -s quit
```
此方式停止步骤是待nginx进程处理任务完毕进行停止。

##### 5. 重启nginx
- 方式1，先停止再启动（建议使用）：
对nginx进行重启相当于先停止nginx再启动nginx，即先执行停止命令再执行启动命令。如下：
```
./nginx -s quit
./nginx
```
- 方式2，重新加载配置文件：
当nginx的配置文件nginx.conf修改后，要想让配置生效需要重启nginx，使用-s reload不用先停止nginx再启动nginx即可将配置信息在nginx中生效，如下：
```
./nginx -s reload
```
##### 6. 安装测试
- 查询nginx进程：`[root@acyou sbin]# ps -ef|grep nginx`
- 访问：`http://192.168.1.116/`

## Nginx的配置文件
`/usr/local/nginx/conf/nginx.conf` 一个server就是一个虚拟主机
#### nginx 配置负载均衡
关键点：**在upstream添加server，在server的location中添加proxy_pass。**
 1. 轮询
      每个请求按时间顺序逐一分配到不同的应用服务器，如果应用服务器down掉，自动剔除，剩下的继续轮询。
 2. 权重
      通过配置权重，指定轮询几率，权重和访问比率成正比，用于应用服务器性能不均的情况。
 3. ip_哈希算法
      每个请求按访问ip的hash结果分配，这样每个访客固定访问一个应用服务器，可以解决session共享的问题。
```
    upstream nginxserver {
        server 192.168.1.116:8081 weight=3;
        server 192.168.1.116:8082 weight=1;
    }

    server {
        listen       80;
        server_name  localhost;

        location / {
            proxy_pass   http://nginxserver;
            index  index.html index.htm;
        }

        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

    }
```
示例：
```
upstream myServer {
    server 192.168.1.116:9090 down;
    server 192.168.1.116:8080 weight=2;
    server 192.168.1.116:6060;
    server 192.168.1.116:7070 backup;
}
```
#### nginx 配置反向代理
**正向代理代理客户端，反向代理代理服务器**
##### 1. 正向代理
我们常说的代理也就是只正向代理，正向代理的过程，它隐藏了真实的请求客户端，服务端不知道真实的客户端是谁，客户端请求的服务都被代理服务器代替来请求，某些科学上网工具扮演的就是典型的正向代理角色。
用浏览器访问 http://www.google.com 时，被残忍的block，于是你可以在国外搭建一台代理服务器，让代理帮我去请求google.com，代理把请求返回的相应结构再返回给我。
##### 2. 反向代理
用户和负载均衡设备直接通信，也意味着用户做服务器域名解析时，解析得到的IP其实是负载均衡的IP，而不是服务器的IP。
在计算机世界里，由于单个服务器的处理客户端（用户）请求能力有一个极限，当用户的接入请求蜂拥而入时，会造成服务器忙不过来的局面，可以使用多个服务器来共同分担成千上万的用户请求，这些服务器提供相同的服务，对于用户来说，根本感觉不到任何差别。


