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
(1,'pyres02','e10adc3949ba59abbe56e057f20f883e',0,'2019-01-11',NULL,NULL,NULL,NULL,NULL,0,'2019-02-16 18:29:27',1),
(2,'test001','fcea920f7412b5da7be0cf42b8c93759',0,'2019-01-17','广西河池','12345678901','1234@qq.com','12345678','1234567',0,NULL,1),
(4,'test002','25d55ad283aa400af464c76d713c07ad',0,'2019-01-18','广西贺州','10001223450','1234@sina.com','98765','wxi456789',0,'2019-01-18 14:38:19',1);

/*Table structure for table `nmorder` */

DROP TABLE IF EXISTS `nmorder`;

CREATE TABLE `nmorder` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(9) NOT NULL,
  `createTime` timestamp NOT NULL,
  `serialNo` varchar(20) COLLATE utf8_bin NOT NULL,
  `total` decimal(8,2) NOT NULL,
  `receiver` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(11) COLLATE utf8_bin DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `nmorder` */

/*Table structure for table `nmorderitem` */

DROP TABLE IF EXISTS `nmorderitem`;

CREATE TABLE `nmorderitem` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `oid` int(10) unsigned NOT NULL,
  `sid` int(10) unsigned NOT NULL,
  `serialNo` varchar(20) COLLATE utf8_bin NOT NULL,
  `shoppingName` varchar(50) COLLATE utf8_bin NOT NULL,
  `introduction` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `shoppingImg` varchar(100) COLLATE utf8_bin NOT NULL,
  `price` decimal(8,2) NOT NULL,
  `count` tinyint(4) NOT NULL,
  `subTotal` decimal(8,2) NOT NULL,
  `remark` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `oid` (`oid`),
  KEY `sid` (`sid`),
  CONSTRAINT `nmorderitem_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `nmorder` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `nmorderitem_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `nmshopping` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `nmorderitem` */

/*Table structure for table `nmshopping` */

DROP TABLE IF EXISTS `nmshopping`;

CREATE TABLE `nmshopping` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `typeId` tinyint(4) NOT NULL,
  `shoppingName` varchar(50) COLLATE utf8_bin NOT NULL,
  `introduction` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `shoppingImg` varchar(400) COLLATE utf8_bin NOT NULL,
  `asPrice` decimal(8,2) unsigned NOT NULL,
  `nmPrice` decimal(8,2) unsigned NOT NULL,
  `store` int(10) unsigned NOT NULL,
  `onShelveTime` timestamp NOT NULL,
  `isSale` tinyint(1) NOT NULL DEFAULT '1',
  `shoppingDetail` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `nmshopping` */

insert  into `nmshopping`(`id`,`typeId`,`shoppingName`,`introduction`,`shoppingImg`,`asPrice`,`nmPrice`,`store`,`onShelveTime`,`isSale`,`shoppingDetail`) values 
(1,1,'缤纷设计鲜花系列','一周一花, 似锦繁花, 五种不同花材, 缤纷多样\r\n该商品为订阅包月鲜花，每1周配送一次\r\n\r\n运费：免运费','/static/home/images/dban2.jpg|/static/home/images/dban1.jpg|',99.98,199.00,999,'2019-02-20 20:08:03',1,NULL),
(2,2,'【送TA】33枝玫瑰系列',NULL,'/static/home/images/h6.jpg|',199.00,299.00,999,'2019-02-22 15:32:54',1,NULL),
(3,3,'【七夕款】许愿流光瓶蓝牙音箱',NULL,'/static/home/images/h12.jpg|',198.00,300.00,999,'2019-02-22 15:34:33',1,NULL),
(4,4,'手工气泡花瓶Victoria',NULL,'/static/home/images/h18.jpg|',109.00,199.00,999,'2019-02-22 15:35:12',1,NULL);

/*Table structure for table `receaddress` */

DROP TABLE IF EXISTS `receaddress`;

CREATE TABLE `receaddress` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL,
  `receiver` varchar(10) COLLATE utf8_bin NOT NULL,
  `phone` varchar(11) COLLATE utf8_bin NOT NULL,
  `address` varchar(200) COLLATE utf8_bin NOT NULL,
  `def` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `uid` (`uid`),
  CONSTRAINT `receaddress_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `receaddress` */

insert  into `receaddress`(`id`,`uid`,`receiver`,`phone`,`address`,`def`) values 
(1,3,'大猪包','18877493606','广西贺州学院西校区南苑',1),
(2,3,'大猪包','18877493606','广西贺州学院西校区南苑',1),
(3,1,'吴若尘1','12345678901','广西贺州学院西校区南苑',1);

/*Table structure for table `return` */

DROP TABLE IF EXISTS `return`;

CREATE TABLE `return` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nmorderItemid` int(10) unsigned NOT NULL,
  `money` decimal(8,2) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `returnTime` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `nmorderItemid` (`nmorderItemid`),
  CONSTRAINT `return_ibfk_1` FOREIGN KEY (`nmorderItemid`) REFERENCES `nmorderitem` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `return` */

/*Table structure for table `shoppingcart` */

DROP TABLE IF EXISTS `shoppingcart`;

CREATE TABLE `shoppingcart` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` mediumint(8) unsigned NOT NULL,
  `sid` int(10) unsigned NOT NULL,
  `count` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`id`,`sid`),
  KEY `uid` (`uid`),
  KEY `sid` (`sid`),
  CONSTRAINT `shoppingcart_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `shoppingcart_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `nmshopping` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `shoppingcart` */

insert  into `shoppingcart`(`id`,`uid`,`sid`,`count`) values 
(1,1,1,3);

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

/*Table structure for table `skshopping` */

DROP TABLE IF EXISTS `skshopping`;

CREATE TABLE `skshopping` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `shoppingName` varchar(50) COLLATE utf8_bin NOT NULL,
  `introduction` text COLLATE utf8_bin,
  `shoppingImg` varchar(300) COLLATE utf8_bin NOT NULL,
  `asPrice` decimal(8,2) unsigned NOT NULL,
  `nmPrice` decimal(8,2) unsigned NOT NULL,
  `store` int(10) unsigned NOT NULL,
  `onShelveTime` timestamp NOT NULL,
  `skTime` timestamp NULL DEFAULT NULL,
  `skTimeEnd` timestamp NOT NULL,
  `skPrice` decimal(8,2) unsigned DEFAULT NULL,
  `isSale` tinyint(1) NOT NULL DEFAULT '1',
  `shoppingDetail` text COLLATE utf8_bin,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `skshopping` */

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
  `email` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '邮箱',
  `qq` varbinary(12) DEFAULT NULL COMMENT 'qq',
  `wechat` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '微信',
  `avatar` varbinary(150) DEFAULT NULL COMMENT '头像',
  `rankId` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '等级',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `isDelete` tinyint(1) NOT NULL DEFAULT '0' COMMENT '软删除',
  `joinTime` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `rank_id` (`rankId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Data for the table `user` */

insert  into `user`(`id`,`nickName`,`memberName`,`password`,`sex`,`birthday`,`phone`,`email`,`qq`,`wechat`,`avatar`,`rankId`,`status`,`isDelete`,`joinTime`) values 
(1,'小猪猪ww','小猪猪ww','123456',0,'2019-02-06','11111111111','111111@qq.com','111111','1111111',NULL,2,1,0,'2019-01-20 22:11:26'),
(3,'吴若尘','若尘','12345678',1,'2019-01-22','17729780297','it.pyres@gmail.com','2148125115','it.pyres','qwrrwqr',0,1,0,'2019-01-22 21:18:55'),
(4,'测试用户1','测试用户1','12345678',1,'2019-02-03','12345678991','123456@qq.com','123456','123456',NULL,2,1,0,'2019-02-04 10:57:14'),
(5,NULL,'吴若尘测试号','111111',1,NULL,NULL,'2148125115@qq.com',NULL,NULL,NULL,0,1,0,'2019-02-24 16:45:31'),
(6,NULL,'吴若尘测试号1','111111',1,NULL,NULL,'999@qq.com',NULL,NULL,NULL,0,1,0,'2019-02-24 16:49:17'),
(7,NULL,'吴若尘测试号3','111111',1,NULL,NULL,'418790777@qq.com',NULL,NULL,NULL,0,1,0,'2019-02-24 19:20:08');

/*!50106 set global event_scheduler = 1*/;

/* Event structure for event `delete_user_unuseful_order_event` */

/*!50106 DROP EVENT IF EXISTS `delete_user_unuseful_order_event`*/;

DELIMITER $$

/*!50106 CREATE DEFINER=`root`@`localhost` EVENT `delete_user_unuseful_order_event` ON SCHEDULE EVERY 1 SECOND STARTS '2019-03-06 20:27:26' ON COMPLETION PRESERVE ENABLE DO call delete_user_unuseful_order_proce() */$$
DELIMITER ;

/* Procedure structure for procedure `delete_user_unuseful_order_proce` */

/*!50003 DROP PROCEDURE IF EXISTS  `delete_user_unuseful_order_proce` */;

DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` PROCEDURE `delete_user_unuseful_order_proce`()
begin
delete from nmorderitem where oid in (SELECT id AS order_id FROM nmorder WHERE createTime < DATE_ADD(NOW(),INTERVAL - 30 MINUTE));
delete from nmorder where  createTime < DATE_ADD(NOW(),INTERVAL - 30 MINUTE);
end */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
