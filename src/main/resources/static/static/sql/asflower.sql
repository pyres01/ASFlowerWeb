/*
SQLyog Ultimate v12.3.1 (64 bit)
MySQL - 5.7.21 : Database - asflower
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`asflower` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;

USE `asflower`;

/*Table structure for table `advert` */

DROP TABLE IF EXISTS `advert`;

CREATE TABLE `advert` (
  `id` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `title` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '广告标题',
  `bgi` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '广告图',
  `link` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '广告链接',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  `endTime` timestamp NOT NULL COMMENT '结束时间',
  `typeId` tinyint(3) unsigned DEFAULT NULL COMMENT '分类id',
  PRIMARY KEY (`id`),
  KEY `typeId` (`typeId`),
  CONSTRAINT `advert_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `adverttype` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `advert` */

/*Table structure for table `adverttype` */

DROP TABLE IF EXISTS `adverttype`;

CREATE TABLE `adverttype` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '广告分类名',
  `remark` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `adverttype` */

/*Table structure for table `column` */

DROP TABLE IF EXISTS `column`;

CREATE TABLE `column` (
  `id` tinyint(2) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '专栏名',
  `remark` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '说明',
  `bgi` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '背景图',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `column` */

/*Table structure for table `coupon` */

DROP TABLE IF EXISTS `coupon`;

CREATE TABLE `coupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '卡券名称',
  `disAmount` int(10) unsigned NOT NULL COMMENT '优惠金额',
  `conAmount` float unsigned NOT NULL COMMENT '使用条件，满对应金额',
  `time` varchar(25) COLLATE utf8_bin NOT NULL COMMENT '有效期，时间段字符串',
  `count` int(10) unsigned NOT NULL COMMENT '数量',
  `typeId` tinyint(3) unsigned NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`),
  KEY `typeId` (`typeId`),
  CONSTRAINT `coupon_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `coupontype` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `coupon` */

/*Table structure for table `coupontype` */

DROP TABLE IF EXISTS `coupontype`;

CREATE TABLE `coupontype` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
  `remark` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `coupontype` */

/*Table structure for table `favoriteshop` */

DROP TABLE IF EXISTS `favoriteshop`;

CREATE TABLE `favoriteshop` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '用户id',
  `shopid` int(10) unsigned NOT NULL COMMENT '商品id',
  `favTime` timestamp NOT NULL COMMENT '收藏时间',
  `isValid` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否失效',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  KEY `shopid` (`shopid`),
  CONSTRAINT `favoriteshop_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `favoriteshop_ibfk_2` FOREIGN KEY (`shopid`) REFERENCES `shop` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `favoriteshop` */

/*Table structure for table `integral` */

DROP TABLE IF EXISTS `integral`;

CREATE TABLE `integral` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '用户id',
  `shopid` int(10) unsigned NOT NULL COMMENT '商品id',
  `score` float NOT NULL COMMENT '积分值',
  `gettime` timestamp NOT NULL COMMENT '获得积分时间',
  `isValid` tinyint(1) NOT NULL DEFAULT '1' COMMENT '积分是否有效',
  KEY `id` (`id`),
  KEY `uid` (`uid`),
  CONSTRAINT `integral_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `integral` */

/*Table structure for table `manager` */

DROP TABLE IF EXISTS `manager`;

CREATE TABLE `manager` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '管理员账号名',
  `password` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '密码，md5',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `address` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '地址',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '联系号码',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(12) COLLATE utf8_bin DEFAULT NULL COMMENT 'qq',
  `wechat` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '微信号',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删除',
  `deleteTime` timestamp NULL DEFAULT NULL COMMENT '删除时间',
  `rank` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `manager` */

insert  into `manager`(`id`,`name`,`password`,`sex`,`birthday`,`address`,`phone`,`email`,`qq`,`wechat`,`isDelete`,`deleteTime`,`rank`) values 
(1,'pyres02','e10adc3949ba59abbe56e057f20f883e',0,'2019-01-11',NULL,NULL,NULL,NULL,NULL,1,'2019-02-16 18:29:27',1),
(2,'test001','fcea920f7412b5da7be0cf42b8c93759',0,'2019-01-17','广西河池','12345678901','1234@qq.com','12345678','1234567',0,NULL,1),
(4,'test002','25d55ad283aa400af464c76d713c07ad',0,'2019-01-18','广西贺州','10001223450','1234@sina.com','98765','wxi456789',0,'2019-01-18 14:38:19',1);

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `orderId` int(10) unsigned NOT NULL COMMENT '订单id',
  `shopStandId` int(10) unsigned NOT NULL COMMENT '商品套装id',
  `price` float unsigned NOT NULL COMMENT '单价',
  `number` smallint(5) unsigned NOT NULL COMMENT '数量',
  `totalAmount` double unsigned NOT NULL COMMENT '金额',
  `createTime` timestamp NOT NULL COMMENT '创建时间',
  `status` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '状态',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`),
  KEY `orderitem_ibfk_1` (`shopStandId`),
  CONSTRAINT `orderitem_ibfk_1` FOREIGN KEY (`shopStandId`) REFERENCES `shopstand` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `orderitem` */

/*Table structure for table `rank` */

DROP TABLE IF EXISTS `rank`;

CREATE TABLE `rank` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '等级名称',
  `logo` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '等级图标',
  `remark` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '等级说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `rank` */

/*Table structure for table `shop` */

DROP TABLE IF EXISTS `shop`;

CREATE TABLE `shop` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '商名全名',
  `title` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '商品标题，全名截取',
  `typeId` tinyint(3) unsigned NOT NULL COMMENT '商品分类id',
  `topicId` tinyint(3) unsigned NOT NULL COMMENT '商品主题id',
  `photos` text COLLATE utf8_bin NOT NULL COMMENT '商品图片，全部图片用|分割',
  `video` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '商品视频',
  `material` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '材料',
  `package` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '包装',
  `remark` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '说明',
  `dispatchPlace` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '发货地',
  `delivery` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '配送物流',
  `zan` mediumint(9) NOT NULL DEFAULT '0' COMMENT '点赞数',
  `bgi` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '背景图，第一张商品图片缩略图',
  `isHot` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否热销',
  `isActivity` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否参加活动',
  `isOnsale` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否在上架',
  PRIMARY KEY (`id`,`remark`),
  KEY `typeId` (`typeId`),
  KEY `topicId` (`topicId`),
  CONSTRAINT `shop_ibfk_1` FOREIGN KEY (`typeId`) REFERENCES `shopcategory` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `shop_ibfk_2` FOREIGN KEY (`topicId`) REFERENCES `topic` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `shop` */

/*Table structure for table `shopcategory` */

DROP TABLE IF EXISTS `shopcategory`;

CREATE TABLE `shopcategory` (
  `id` tinyint(3) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '分类名',
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `shopcategory` */

/*Table structure for table `shopstand` */

DROP TABLE IF EXISTS `shopstand`;

CREATE TABLE `shopstand` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `shopid` int(10) unsigned NOT NULL COMMENT '商品id',
  `marketPrice` double NOT NULL COMMENT '市场价',
  `salePrice` double NOT NULL COMMENT '销售价',
  `activityPrice` double DEFAULT NULL COMMENT '活动价',
  `freegift` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '附送赠品',
  `stock` int(11) NOT NULL COMMENT '库存',
  `status` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `shopid` (`shopid`),
  CONSTRAINT `shopstand_ibfk_1` FOREIGN KEY (`shopid`) REFERENCES `shop` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `shopstand` */

/*Table structure for table `siteinfo` */

DROP TABLE IF EXISTS `siteinfo`;

CREATE TABLE `siteinfo` (
  `name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '站点名称',
  `introduce` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '站点介绍',
  `author` varchar(10) COLLATE utf8_bin DEFAULT NULL COMMENT '网站作者',
  `aboutAuthor` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '关于网站作者',
  `logoCol` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '网站logo，带色彩',
  `logoDef` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '网址logo，无色彩',
  `shortcutIcon` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '网址图片，shortcut',
  `brandSlogan` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '站点口号',
  `keywork` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '关键字',
  `copyright` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '版权信息',
  `qrcode` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '网址二维码',
  `privacyClause` text COLLATE utf8_bin COMMENT '隐私条约'
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `siteinfo` */

/*Table structure for table `topic` */

DROP TABLE IF EXISTS `topic`;

CREATE TABLE `topic` (
  `id` tinyint(2) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '主题名',
  `pid` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '父类id',
  `remark` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '说明',
  `bgi` varchar(150) COLLATE utf8_bin NOT NULL COMMENT '背景图',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `topic` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `nickName` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT '昵称',
  `memberName` varchar(15) COLLATE utf8_bin NOT NULL COMMENT '会员名',
  `password` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '密码',
  `sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '性别',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL COMMENT '号码',
  `email` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `qq` varbinary(12) DEFAULT NULL COMMENT 'qq',
  `wechat` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '微信',
  `avatar` varbinary(150) DEFAULT NULL COMMENT '头像',
  `rankId` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '等级',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删除',
  `joinTime` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rank_id` (`rankId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user` */

insert  into `user`(`id`,`nickName`,`memberName`,`password`,`sex`,`birthday`,`phone`,`email`,`qq`,`wechat`,`avatar`,`rankId`,`status`,`isDelete`,`joinTime`) values 
(1,'小猪猪ww','小猪猪ww','11111111',0,'2019-02-06','11111111111','111111@qq.com','111111','1111111',NULL,2,1,0,'2019-01-20 22:11:26'),
(3,'吴若尘','若尘','12345678',1,'2019-01-22','17729780297','it.pyres@gmail.com','2148125115','it.pyres','qwrrwqr',0,1,0,'2019-01-22 21:18:55'),
(4,'测试用户1','测试用户1','12345678',0,'2019-02-03','12345678991','123456@qq.com','123456','123456',NULL,2,1,0,'2019-02-04 10:57:14');

/*Table structure for table `usercoupon` */

DROP TABLE IF EXISTS `usercoupon`;

CREATE TABLE `usercoupon` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `uid` mediumint(8) unsigned NOT NULL COMMENT '用户id',
  `couponId` int(10) unsigned NOT NULL COMMENT '优惠券id',
  `getTime` timestamp NOT NULL COMMENT '获得时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `usercoupon` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
