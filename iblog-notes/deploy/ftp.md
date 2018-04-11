### FTP

#### vsftpd 安装
1. 安装服务
	`[root@acyou /]#  yum -y install vsftpd`
2. 关闭匿名访问：
	`[root@acyou youfang]# vi /etc/vsftpd/vsftpd`
		anonymous_enable=NO
		pasv_min_port=30000
		pasv_max_port=30999
3. 设置开机自启
	`[root@acyou youfang]# chkconfig vsftpd on`
#### 无法登录？
```
[root@acyou youfang]# getsebool -a | grep ftp
allow_ftpd_anon_write --> off
allow_ftpd_full_access --> off
allow_ftpd_use_cifs --> off
allow_ftpd_use_nfs --> off
ftp_home_dir --> off
ftpd_connect_db --> off
ftpd_use_fusefs --> off
ftpd_use_passive_mode --> off
httpd_enable_ftp_server --> off
tftp_anon_write --> off
tftp_use_cifs --> off
tftp_use_nfs --> off

[root@acyou youfang]# setsebool -P allow_ftpd_full_access on
[root@acyou youfang]# setsebool -P ftp_home_dir on
```
#### 安装完成

浏览器访问：`ftp://192.168.1.116/`
命令行访问：
```
PS C:\WINDOWS\system32> ftp
ftp> open 192.168.1.116
```