-- 初始化iblog	MySQL
-- version:ib 2.2.3
-- date:2018-01-12

DROP DATABASE IF EXISTS 'iblog';
create DATABASE iblog;
use iblog;
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `ib_user`;
CREATE TABLE `ib_user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(32) NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(20) DEFAULT '' COMMENT '昵称',
  `role` varchar(10) DEFAULT '' COMMENT '角色',
  `enable` enum('n','y') DEFAULT 'y' COMMENT '是否启用',
  `photo` varchar(255) DEFAULT '' COMMENT '头像',
  `email` varchar(60) NOT NULL DEFAULT '' COMMENT '邮箱',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';
INSERT INTO `ib_user` VALUES ('0', 'caocao', 'caocao123', 'caocao', '用户', 'y', '../content/uploadfile/phpto/2.jpg', 'caocao@acyou.cn', '这个家伙很懒', '2017-10-25 21:03:09');
INSERT INTO `ib_user` VALUES ('1', 'demo', 'demo123', 'demo', '用户', 'y', '', 'demo@acyou.cn', '', '2017-10-23 21:03:09');
INSERT INTO `ib_user` VALUES ('2', 'youfang', '19841c4b3b26dab7a645489b1f72b040', null, null, null, null, 'youfang@acyou.cn', null, '2017-11-04 22:39:17');
INSERT INTO `ib_user` VALUES ('3', 'admin', 'c2dfdebcb7f1aa1329df9f1c780450cc', null, null, null, null, '981545521@qq.com', null, '2017-11-23 12:40:19');
INSERT INTO `ib_user` VALUES ('4', 'Gcy_A1', 'e8c2e9af23e496eb4e87e3ebad6bef43', '白天不懂夜的黑', '管理员', 'y', '', '1361264477@qq.com', null, '2018-01-07 16:04:26');

-- ----------------------------
-- 文章分组(分类)
-- ----------------------------
DROP TABLE IF EXISTS `ib_sort`;
CREATE TABLE `ib_sort` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sort_name` varchar(255) NOT NULL DEFAULT '' COMMENT '分类名称',
  `uid` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '所属用户ID',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifiedtime` datetime DEFAULT NULL COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='文章分组(分类)';
INSERT INTO `ib_sort` VALUES ('1', '唐宋文学', '1', '宋代诗词收藏', '2017-06-12 22:00:29', null, '0');
INSERT INTO `ib_sort` VALUES ('2', 'JQuery', '2', 'JQuery是一个流行的前端框架。', '2017-09-07 14:58:00', null, '0');
INSERT INTO `ib_sort` VALUES ('3', 'Linux', '8', 'Linux是一个流行的系统', '2017-09-07 14:58:15', null, '0');
INSERT INTO `ib_sort` VALUES ('4', 'JavaScript', '9', 'JS是一个支持在浏览器端解释执行的语言！', '2017-09-07 14:58:27', null, '0');
INSERT INTO `ib_sort` VALUES ('5', 'Java', '1', '还用说吗？这还用说吗?', '2017-09-07 14:58:40', null, '0');
INSERT INTO `ib_sort` VALUES ('6', 'HTML+CSS哦', '1', '前端的知识还是灰常重要滴！', '2017-09-07 14:58:53', null, '0');
INSERT INTO `ib_sort` VALUES ('7', 'Spring', '1', 'Java的春天！！', '2017-09-07 20:25:04', null, '0');

-- ----------------------------
-- 邮箱激活码：
-- ----------------------------
DROP TABLE IF EXISTS `ib_activecode`;
CREATE TABLE `ib_activecode` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(255) NOT NULL DEFAULT '' COMMENT '邮箱',
  `activecode` varchar(10) DEFAULT '' COMMENT '激活码',
  `used` enum('n','y') DEFAULT 'n' COMMENT '是否已经使用',
  `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='邮箱激活码';
INSERT INTO `ib_activecode` VALUES ('1', 'youfang@acyou.cn', 'asdfg', 'y', '2017-10-25 21:03:18');
INSERT INTO `ib_activecode` VALUES ('2', 'youfang@acyou.cn', 'Cier4', 'n', '2017-11-04 22:38:58');
INSERT INTO `ib_activecode` VALUES ('3', '981545521@qq.com', 'yZMAt', 'n', '2017-11-23 12:40:03');
INSERT INTO `ib_activecode` VALUES ('4', '1361264477@qq.com', 'wwmTG', 'n', '2018-01-07 16:03:44');

-- ----------------------------
-- 文章
-- ----------------------------
DROP TABLE ib_blog;
CREATE TABLE `ib_blog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章主键id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  `content` varchar(4000) DEFAULT '' COMMENT '文章内容',
  `excerpt` varchar(1000) DEFAULT '' COMMENT '摘要',
  `type` varchar(20) NOT NULL DEFAULT 'blog' COMMENT '文章类型:blog/essay/shuoshuo',
  `id_user` int(10) NOT NULL DEFAULT '0' COMMENT '用户表主键',
  `id_sort` int(10) DEFAULT '0' COMMENT '分类表主键',
  `id_attachment` int(10) DEFAULT NULL COMMENT '附件表主键',
  `top` char(1) DEFAULT '2' COMMENT '是否置顶：1置顶，2不置顶',
  `hide` char(1) DEFAULT '1' COMMENT '是否隐藏（公开）：1公开，2不公开',
  `fabulous` int(11) DEFAULT NULL COMMENT '获赞数量',
  `comment_number` int(11) DEFAULT '0' COMMENT '评论数量',
  `allow_comment` int(11) DEFAULT '1' COMMENT '允许评论：1允许，2不允许',
  `creationtime` datetime NOT NULL COMMENT '创建时间',
  `modifiedtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次修改时间',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章';

-- ----------------------------
-- Records of ib_blog
-- ----------------------------
INSERT INTO `ib_blog` VALUES ('1', 'javascript对象', '什么是面向对象： 就是把数据及数据的操作方法放在一起，作为一个相互依存的整体----对象。对同类对象抽象出其共性，形成类。 类中的大多数据，只能用本类的方法进行处理。类通过一个简单的外部接口与外界发生关系，对象与对象之间通过消息进行通信。程序流程由用户在使用中决定。 名词解释： 基于对象：一切皆对象，以对象的概念来编程。 面向对象编程： 对象：就是人们要研究的事物，不仅能表示具体事物，还能表示抽象的规则，计划或事件。 属性的无序集合，每个属性可以存一个值(原始值，对象，函数) 对象的属性和行为： 属性：用数据值来描述他的状态 行为：用来改变对象行为的方法', '什么是面向对象： 就是把数据及数据的操作方法放在一起，作为一个相互依存的整体----对象。对同类对象抽象出其共性，形成类。 类中的大多数据，只能用本类的方法进行处理。类通过一个简单的外部接口与外界发生关系，对象与对象之间通过消息进行通信。程序流程由用户在使用中决定。 名词解释： 基于对象：一切皆对象，以对象的概念来编程。 面向对象编程： 对象：就是人们要研究的事物，不仅能表示具体事物，还能表示抽象的规则，计划或事件。 属性的无序集合，每个属性可以存一个值(原始值，对象，函数) 对象的属性和行为： 属性：用数据值来描述他的状态 行为：用来改变对象行为的方法', 'blog', '1', '4', null, '2', '1', '4', '1', '1', '2018-01-09 22:41:11', '2018-03-01 22:41:17',0);
INSERT INTO `ib_blog` VALUES ('2', '多线程【Thread、线程创建】', '\r\n主线程:执行主方法的线程,就叫做主线程 单线程程序:程序从mani开始从上到下依次运行 程序从main方法开始运行,JVM运行main方法,会找操作系统 开辟一条通向cpu的执行路径,cpu可以通过这条路径来执行main方法 这条路径有一个名字叫主(main)线程 创建线程方式一继承Thread类 实现步骤: 1.创建Thread类的子类 2.重写Thread类中的run方法,设置线程的任务 3.创建Thread类的子类对象 4.调用Thread类中的start方法开启一个新的线程,执行run方法 使该线程开始执行；Java 虚拟机调用该线程的 run 方法。 结果是两个线程并发地运行；当前线程（main线程）和另一个线程（执行 run 方法的线程）。 多次启动一个线程是非法的。特别是当线程已经结束执行后，不能再重新启动。\r\n', '\r\n主线程:执行主方法的线程,就叫做主线程 单线程程序:程序从mani开始从上到下依次运行 程序从main方法开始运行,JVM运行main方法,会找操作系统 开辟一条通向cpu的执行路径,cpu可以通过这条路径来执行main方法 这条路径有一个名字叫主(main)线程 创建线程方式一继承Thread类 实现步骤: 1.创建Thread类的子类 2.重写Thread类中的run方法,设置线程的任务 3.创建Thread类的子类对象 4.调用Thread类中的start方法开启一个新的线程,执行run方法 使该线程开始执行；Java 虚拟机调用该线程的 run 方法。 结果是两个线程并发地运行；当前线程（main线程）和另一个线程（执行 run 方法的线程）。 多次启动一个线程是非法的。特别是当线程已经结束执行后，不能再重新启动。\r\n', 'blog', '1', '4', null, '2', '1', '4', '1', '1', '2018-01-09 22:41:11', '2018-03-01 22:41:17',0);
INSERT INTO `ib_blog` VALUES ('3', 'Java中的抽象类和接口', '\r\n想要了解接口我们要先知道抽象类。那么什么是抽象类呢？ 问题描述：生活中我们有很多的对象是无法具体描述的，比如说：我们可以说四边形有四条边。或者具体点说就是矩形两边对称且相等，正方形四边对称且相等。但是对于普通的图形而言就很难具体描述了。转换成Java语言就是说：对于一个很具体的类我们可以很方便的定义它的各种属性和方法，但是对于有一些类我们却难以了解它的方法时如何实现的。这时我们就可以用到抽象类。', '\r\n想要了解接口我们要先知道抽象类。那么什么是抽象类呢？ 问题描述：生活中我们有很多的对象是无法具体描述的，比如说：我们可以说四边形有四条边。或者具体点说就是矩形两边对称且相等，正方形四边对称且相等。但是对于普通的图形而言就很难具体描述了。转换成Java语言就是说：对于一个很具体的类我们可以很方便的定义它的各种属性和方法，但是对于有一些类我们却难以了解它的方法时如何实现的。这时我们就可以用到抽象类。', 'blog', '1', '4', null, '2', '1', '4', '1', '1', '2018-01-09 22:41:11', '2018-03-01 22:41:17',0);
INSERT INTO `ib_blog` VALUES ('4', '网络通信', '\r\n一 概述 1.网络模型 OSI(Open System Interconnection，开放式系统互联)模型，是对网络系统结构的概括，将网络分为七层：应用层、表示层、会话层、传输层、网络层、数据链路层、物理层。 2.IP协议 网络层协议，规定了在互联网上确定与寻找计算机的规则。 3.TCP协议 传输层的一种数据传输协议，数据传输前通过“三次握手”建立连接，然后再发送数据，适用于对数据准确性要求较高的情况，由于数据传输前需要建立连接，传输速度较慢。 4.UDP协议 传输层的一种数据传输协议，数据传输前不需要建立连接，适用于对数据准确性要求不高的情况，传输数据较快，一般聊天信息都通过该协议传输。 5.HTTP协议 HTTP协议属于应用层协议，为操作系统或网络应用程序提供访问网络服务的接口。 6.端口port 当数据到达计算机后，为了找到目标应用程序，为每一个应用程序分配了一个整数值，取值0-65535，这个整数值就是端口，从中可以看出，端口代表了计算机上一个应用程序，保证数据准确到达预定的程序。一个端口不能同时被多个应用程序占用，一个应用程序结束以后，端口不会立即释放，有一个内存延迟占有的时间，这个时间一般很短。端口、0-1023已经被系统应用程序及其他应用程序占用，程序设计时避免使用这个范围的端口。 7.套接字Socket 套接字是数据发送与接收的工具。发送者通过套接字发送数据，接受者通过套接字监听指定的端口获取数据。 8.无论采用TCP协议，还是UDP协议，数据都只能以字节形式发送。 二 TCP程序设计 1.关闭通过Socket获取的输入流或者输出流将关闭Socket。 2.通过Socket获取的输出流输出完毕后必须关闭，不然另一端对应的输入流将阻塞。由于通过输出流对象关闭输出流时，同时关闭Socket对象，将导致另一端无法获取对应Socket的对象，因此只能通过Socket下的方法shutdownOutput关闭输出流。', '\r\n一 概述 1.网络模型 OSI(Open System Interconnection，开放式系统互联)模型，是对网络系统结构的概括，将网络分为七层：应用层、表示层、会话层、传输层、网络层、数据链路层、物理层。 2.IP协议 网络层协议，规定了在互联网上确定与寻找计算机的规则。 3.TCP协议 传输层的一种数据传输协议，数据传输前通过“三次握手”建立连接，然后再发送数据，适用于对数据准确性要求较高的情况，由于数据传输前需要建立连接，传输速度较慢。 4.UDP协议 传输层的一种数据传输协议，数据传输前不需要建立连接，适用于对数据准确性要求不高的情况，传输数据较快，一般聊天信息都通过该协议传输。 5.HTTP协议 HTTP协议属于应用层协议，为操作系统或网络应用程序提供访问网络服务的接口。 6.端口port 当数据到达计算机后，为了找到目标应用程序，为每一个应用程序分配了一个整数值，取值0-65535，这个整数值就是端口，从中可以看出，端口代表了计算机上一个应用程序，保证数据准确到达预定的程序。一个端口不能同时被多个应用程序占用，一个应用程序结束以后，端口不会立即释放，有一个内存延迟占有的时间，这个时间一般很短。端口、0-1023已经被系统应用程序及其他应用程序占用，程序设计时避免使用这个范围的端口。 7.套接字Socket 套接字是数据发送与接收的工具。发送者通过套接字发送数据，接受者通过套接字监听指定的端口获取数据。 8.无论采用TCP协议，还是UDP协议，数据都只能以字节形式发送。 二 TCP程序设计 1.关闭通过Socket获取的输入流或者输出流将关闭Socket。 2.通过Socket获取的输出流输出完毕后必须关闭，不然另一端对应的输入流将阻塞。由于通过输出流对象关闭输出流时，同时关闭Socket对象，将导致另一端无法获取对应Socket的对象，因此只能通过Socket下的方法shutdownOutput关闭输出流。', 'blog', '1', '4', null, '2', '1', '4', '1', '1', '2018-01-09 22:41:11', '2018-03-01 22:41:17',0);
INSERT INTO `ib_blog` VALUES ('5', 'IO【转换流,打印流,序列化】', '\r\n编码: 把看的懂,变成看不懂的 String str = \"中国\"; byte[] bytes = str.getBytes(); System.out.println(Arrays.toString(bytes)); 解码: 把看不懂的内容,变成能看懂的 String s = new String(bytes); System.out.println(s); java.io.OutputStreamWriter extends Writer OutputStreamWriter:转换流 作用:是字符流通向字节流的桥梁,可以指定编码表 继承自父类Writer的公共成员方法 写一个字符,写字符数组,写字符数组的一部分,写字符串,写字符的一部分,刷新,关闭 构造方法: OutputStreamWriter(OutputStream out, String charsetName) 创建使用指定字符集的 OutputStreamWriter。 参数: OutputStream out:字节输出流(把转换后的字节写入到文件中) 可以传入FileOutputStream String charsetName:编码表名称 可以传入一个字符串格式的编码表名称,比如\"GBK\",\"utf-8\"...,编码表名称不区分大小写,如果不写默认为系统码表 使用步骤: 1.创建字符输出流FileOutputStream,绑定数据的目的地 2.创建转换流OutputStreamWriter对象,构造方法中传入FileOutputStream和指定的编码表名称 3.调用OutputStreamWriter中写数据的方法,把数据写入到内存缓冲区中 4.释放资源,并把数据刷新到文件中', '\r\n编码: 把看的懂,变成看不懂的 String str = \"中国\"; byte[] bytes = str.getBytes(); System.out.println(Arrays.toString(bytes)); 解码: 把看不懂的内容,变成能看懂的 String s = new String(bytes); System.out.println(s); java.io.OutputStreamWriter extends Writer OutputStreamWriter:转换流 作用:是字符流通向字节流的桥梁,可以指定编码表 继承自父类Writer的公共成员方法 写一个字符,写字符数组,写字符数组的一部分,写字符串,写字符的一部分,刷新,关闭 构造方法: OutputStreamWriter(OutputStream out, String charsetName) 创建使用指定字符集的 OutputStreamWriter。 参数: OutputStream out:字节输出流(把转换后的字节写入到文件中) 可以传入FileOutputStream String charsetName:编码表名称 可以传入一个字符串格式的编码表名称,比如\"GBK\",\"utf-8\"...,编码表名称不区分大小写,如果不写默认为系统码表 使用步骤: 1.创建字符输出流FileOutputStream,绑定数据的目的地 2.创建转换流OutputStreamWriter对象,构造方法中传入FileOutputStream和指定的编码表名称 3.调用OutputStreamWriter中写数据的方法,把数据写入到内存缓冲区中 4.释放资源,并把数据刷新到文件中', 'blog', '1', '4', null, '2', '1', '4', '1', '1', '2018-01-09 22:41:11', '2018-03-01 22:41:17',0);
INSERT INTO `ib_blog` VALUES ('6', 'Java数据类型(2)------自动封装拆箱', '\r\n目的: 自动装箱和拆箱从Java 1.5开始引入，目的是将原始类型值转自动地转换成对应的对象,以使用对象的API和引用类型操作。自动装箱与拆箱的机制可以让我们在Java的变量赋值或者是方法调用等情况下使用原始类型或者对象类型更加简单直接。 定义: 自动装箱就是Java自动将原始类型值转换成对应的对象，比如将int的变量转换成Integer对象，这个过程叫做装箱，反之将Integer对象转换成int类型值，这个过程叫做拆箱。因为这里的装箱和拆箱是自动进行的非人为转换，所以就称作为自动装箱和拆箱。原始类型byte,short,char,int,long,float,double和boolean对应的封装类为Byte,Short,Character,Integer,Long,Float,Double,Boolean。 实现： 自动装箱时编译器调用valueOf将原始类型值转换成对象，同时自动拆箱时，编译器通过调用类似intValue(),doubleValue()这类的方法将对象转换成原始类型值。', '\r\n目的: 自动装箱和拆箱从Java 1.5开始引入，目的是将原始类型值转自动地转换成对应的对象,以使用对象的API和引用类型操作。自动装箱与拆箱的机制可以让我们在Java的变量赋值或者是方法调用等情况下使用原始类型或者对象类型更加简单直接。 定义: 自动装箱就是Java自动将原始类型值转换成对应的对象，比如将int的变量转换成Integer对象，这个过程叫做装箱，反之将Integer对象转换成int类型值，这个过程叫做拆箱。因为这里的装箱和拆箱是自动进行的非人为转换，所以就称作为自动装箱和拆箱。原始类型byte,short,char,int,long,float,double和boolean对应的封装类为Byte,Short,Character,Integer,Long,Float,Double,Boolean。 实现： 自动装箱时编译器调用valueOf将原始类型值转换成对象，同时自动拆箱时，编译器通过调用类似intValue(),doubleValue()这类的方法将对象转换成原始类型值。', 'blog', '1', '4', null, '2', '1', '4', '1', '1', '2018-01-09 22:41:11', '2018-03-01 22:41:17',0);


-- ----------------------------
-- 评论
-- ----------------------------
-- --评论ID--所属博客,对应ib_blog_id	--创建时间	--评论人	--评论内容	--QQ	--评论人IP --隐藏评论
DROP TABLE ib_comment;
CREATE TABLE ib_comment (
  id int PRIMARY KEY,
  bid int,
  createtime date DEFAULT sysdate,
  poster varchar(20),
  commentary varchar(4000),
  qq int,
  ip varchar(400),
  hide char(1) DEFAULT 'n'
);

INSERT INTO ib_comment VALUES(seq_comment_id.nextval,'1',sysdate,'youfang','写的很好,很不错',10000,'117.136.45.149','n');

commit;




-- ----------------------------
-- 附件
-- ----------------------------
-- --附件id--所属博客id--文件名--文件大小--文件路径	--添加时间
DROP TABLE IF EXISTS ib_attachment;
CREATE TABLE ib_attachment (
  id int PRIMARY KEY,
  bid int DEFAULT '1',
  filename varchar(255),
  filesize int,
  filepath varchar(255),
  addtime date DEFAULT sysdate
);

INSERT INTO ib_attachment VALUES(seq_attachment_id.nextval,'1','setup.rar','418313','../content/uploadfile/201702/b47e1486545896.rar',sysdate);

commit;



-- ----------------------------
-- TODO : 留言
-- ----------------------------
--






-- ----------------------------
-- TODO : 评论回复
-- ----------------------------
--
DROP TABLE ib_reply;
CREATE TABLE ib_reply (
  id int PRIMARY KEY,
  tid int,
  createtime date DEFAULT sysdate,
  name varchar(20),
  content varchar(4000),
  ip varchar(128)
);
