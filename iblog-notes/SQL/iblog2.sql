-- 初始化iblog	MySQL
-- version:ib 2.0.0
-- date:2017-06-12

DROP DATABASE IF EXISTS 'ib';
create DATABASE ib;
use ib;
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
INSERT INTO `ib_sort` VALUES ('1', '唐宋文学', '0', '宋代诗词收藏', '2017-10-25 21:03:18', '2017-11-01 00:00:00', '0');
INSERT INTO `ib_sort` VALUES ('2', 'asds', '1', 'fsdfsdf', '2017-11-22 15:55:43', '2017-11-02 16:47:53', '0');
INSERT INTO `ib_sort` VALUES ('3', 'asdseew', '1', 're', '2017-11-22 15:55:46', '2017-11-03 16:47:58', '0');
INSERT INTO `ib_sort` VALUES ('4', 'werw', '1', 'ert', '2017-11-22 15:55:47', '2017-11-04 16:48:03', '0');
INSERT INTO `ib_sort` VALUES ('5', '234', '1', '234', '2017-11-22 15:55:48', null, '0');
INSERT INTO `ib_sort` VALUES ('6', '435', '1', '455', '2017-11-22 15:55:49', null, '0');
INSERT INTO `ib_sort` VALUES ('7', '天气越来越冷啦', '1', '不开空调不行啦', '2017-11-23 11:50:00', '2017-11-23 11:50:00', '0');
INSERT INTO `ib_sort` VALUES ('8', '鼎折覆餗', '5', '是否颠倒是非', '2017-11-23 12:41:11', null, '0');
INSERT INTO `ib_sort` VALUES ('9', '佛山市', '5', '是的发送个任务', '2017-11-23 12:41:23', null, '0');
INSERT INTO `ib_sort` VALUES ('10', '乐观的', '6', '测试大法', '2018-01-07 18:36:36', null, '0');
INSERT INTO `ib_sort` VALUES ('11', '明天你是否依然爱我', '6', '缠绵是最好四周冰山都仰慕', '2018-01-07 16:10:06', null, '0');
INSERT INTO `ib_sort` VALUES ('12', '爱情陷阱', '6', '我两到底认识过', '2018-01-07 16:34:21', '2018-01-07 16:34:21', '0');

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
-- 文章id--标题--创建时间--最后一次修改时间--内容--摘要--类型:blog/随笔/说说--作者,对应ib_user_id--分类,对应ib_sort_id--附件,对应ib_attachment_id--置顶--是否隐藏
DROP TABLE ib_blog;
CREATE TABLE ib_blog (
  bid		 int(10) unsigned NOT NULL AUTO_INCREMENT,
  title		 varchar(255)  NOT NULL DEFAULT '',
  createtime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  modifytime  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  content	 varchar(4000) DEFAULT '',
  excerpt	 varchar(1000) DEFAULT '',
  type 		 varchar(20) DEFAULT 'blog',
  uid	 	 int(10) DEFAULT '0',
  sid		 int(10) DEFAULT '0',
  aid 		 int(10) DEFAULT '0',
  top		 char(1) DEFAULT 'n',
  hide 		 char(1) DEFAULT 'n',
  views		 int(10),
  comnum     int(10),
  allow_remark  enum('n','y') DEFAULT 'y',
  PRIMARY KEY (bid)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='文章';

INSERT INTO ib_blog VALUES('1','兰亭集序',default,default,'夫人之相与，俯仰一世，或取诸怀抱，悟言一室之内；或因寄所托，放浪形骸之外。虽趣舍万殊，静躁不同，当其欣于所遇，暂得于己，快然自足，曾不知老之将至。及其所之既倦，情随事迁，感慨系之矣。向之所欣，俯仰之间，已为陈迹，犹不能不以之兴怀。况修短随化，终期于尽。古人云：“死生亦大矣。”岂不痛哉！','王羲之的作品','blog','1','1','1','n','n');



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
