/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.61 : Database - APP_FACTORY
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `ad_list` */

DROP TABLE IF EXISTS `ad_list`;

CREATE TABLE `ad_list` (
  `ad_id` int(10) NOT NULL AUTO_INCREMENT,
  `shop_id` varchar(20) DEFAULT NULL,
  `ad_name` char(100) DEFAULT NULL,
  `ad_icon` varchar(100) DEFAULT NULL,
  `ad_weburl` varchar(200) DEFAULT NULL,
  `ad_status` char(10) DEFAULT NULL,
  `ad_lastupdate` char(20) DEFAULT NULL,
  PRIMARY KEY (`ad_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=gbk;

/*Data for the table `ad_list` */

insert  into `ad_list`(`ad_id`,`shop_id`,`ad_name`,`ad_icon`,`ad_weburl`,`ad_status`,`ad_lastupdate`) values (1,'5','我心飞翔','/upload/image/20140915/201409152355137.png','www.fie.com.cn','1','2014-09-15 23:55:57');

/*Table structure for table `article_list` */

DROP TABLE IF EXISTS `article_list`;

CREATE TABLE `article_list` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` char(11) DEFAULT NULL,
  `article_connect_id` char(11) DEFAULT NULL,
  `article_cat_id` char(11) DEFAULT NULL,
  `article_icon` varchar(200) DEFAULT NULL,
  `article_title` varchar(100) DEFAULT NULL,
  `article_description` varchar(100) DEFAULT NULL,
  `article_content` varchar(2000) DEFAULT NULL,
  `article_keywords` varchar(200) DEFAULT NULL,
  `article_status` smallint(6) DEFAULT NULL,
  `article_create_userid` char(11) DEFAULT NULL,
  `article_publish_userid` char(11) DEFAULT NULL,
  `article_publish_time` char(20) DEFAULT NULL,
  `article_create_time` char(20) DEFAULT NULL,
  `article_lastupdate` char(20) DEFAULT NULL,
  `article_web_url` varchar(200) DEFAULT NULL,
  `article_observe1` varchar(200) DEFAULT NULL,
  `article_observe2` varchar(200) DEFAULT NULL,
  `article_observe3` varchar(200) DEFAULT NULL,
  `article_observe4` varchar(200) DEFAULT NULL,
  `article_observe5` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=gbk;

/*Data for the table `article_list` */

insert  into `article_list`(`article_id`,`shop_id`,`article_connect_id`,`article_cat_id`,`article_icon`,`article_title`,`article_description`,`article_content`,`article_keywords`,`article_status`,`article_create_userid`,`article_publish_userid`,`article_publish_time`,`article_create_time`,`article_lastupdate`,`article_web_url`,`article_observe1`,`article_observe2`,`article_observe3`,`article_observe4`,`article_observe5`) values (1,'1','2','2','ic','今日头条','desc','con','key',1,'1','1','2014-08-30 00:00:00','2014-08-30 00:00:00','2014-08-30 00:00:00','www.si.com',NULL,NULL,NULL,NULL,NULL);
insert  into `article_list`(`article_id`,`shop_id`,`article_connect_id`,`article_cat_id`,`article_icon`,`article_title`,`article_description`,`article_content`,`article_keywords`,`article_status`,`article_create_userid`,`article_publish_userid`,`article_publish_time`,`article_create_time`,`article_lastupdate`,`article_web_url`,`article_observe1`,`article_observe2`,`article_observe3`,`article_observe4`,`article_observe5`) values (2,'1','4','4','icon','行业新闻','dede','新闻内容','头条',1,'1','1','2014-08-30 00:00:00','2014-08-30 00:00:00','2014-08-30 00:00:00','www.xx.com',NULL,NULL,NULL,NULL,NULL);
insert  into `article_list`(`article_id`,`shop_id`,`article_connect_id`,`article_cat_id`,`article_icon`,`article_title`,`article_description`,`article_content`,`article_keywords`,`article_status`,`article_create_userid`,`article_publish_userid`,`article_publish_time`,`article_create_time`,`article_lastupdate`,`article_web_url`,`article_observe1`,`article_observe2`,`article_observe3`,`article_observe4`,`article_observe5`) values (3,'1','4','0','sd','dfd','dfd','dfd','dfd',1,'0','0',NULL,'2014-09-12 21:57:24','2014-09-12 21:57:24','dfd',NULL,NULL,NULL,NULL,NULL);
insert  into `article_list`(`article_id`,`shop_id`,`article_connect_id`,`article_cat_id`,`article_icon`,`article_title`,`article_description`,`article_content`,`article_keywords`,`article_status`,`article_create_userid`,`article_publish_userid`,`article_publish_time`,`article_create_time`,`article_lastupdate`,`article_web_url`,`article_observe1`,`article_observe2`,`article_observe3`,`article_observe4`,`article_observe5`) values (4,'5','5','0','/upload/image/20140915/201409152139326.png','习近平彭丽媛抵马尔代夫 乘船赴下榻宾馆','xxxxxx','/upload/html/20140915/201409152139204.html','',1,'5','5',NULL,'2014-09-15 21:39:34','2014-09-15 21:52:53','/upload/html/20140915/201409152139204.html',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `message_list` */

DROP TABLE IF EXISTS `message_list`;

CREATE TABLE `message_list` (
  `message_id` int(11) NOT NULL,
  `message_shopid` int(11) DEFAULT NULL,
  `message_connectid` int(11) DEFAULT NULL,
  `message_class` int(11) DEFAULT NULL,
  `message_type` int(11) DEFAULT NULL,
  `message_user_id` int(11) DEFAULT NULL,
  `message_note` varchar(500) DEFAULT NULL,
  `message_pic` varchar(500) DEFAULT NULL,
  `message_status` int(11) DEFAULT NULL,
  `message_parentid` int(11) DEFAULT NULL,
  `message_sendtime` date DEFAULT NULL,
  `message_lastupdate` date DEFAULT NULL,
  `message_observe1` varchar(100) DEFAULT NULL,
  `message_observe2` varchar(100) DEFAULT NULL,
  `message_observe3` varchar(100) DEFAULT NULL,
  `message_observe4` varchar(200) DEFAULT NULL,
  `message_observe5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `message_list` */

/*Table structure for table `module_list` */

DROP TABLE IF EXISTS `module_list`;

CREATE TABLE `module_list` (
  `module_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `module_list_name` char(50) DEFAULT NULL,
  `module_list_status` int(11) DEFAULT NULL,
  `module_list_createtime` datetime DEFAULT NULL,
  `module_list_lastupdate` datetime DEFAULT NULL,
  `module_list_parent_id` int(11) DEFAULT NULL,
  `module_list_desc` char(100) DEFAULT NULL,
  `module_list_observe1` varchar(200) DEFAULT NULL,
  `module_list_observe2` varchar(200) DEFAULT NULL,
  `module_list_observe3` varchar(200) DEFAULT NULL,
  `module_list_observe4` varchar(200) DEFAULT NULL,
  `module_list_observe5` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`module_list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=gbk;

/*Data for the table `module_list` */

insert  into `module_list`(`module_list_id`,`module_list_name`,`module_list_status`,`module_list_createtime`,`module_list_lastupdate`,`module_list_parent_id`,`module_list_desc`,`module_list_observe1`,`module_list_observe2`,`module_list_observe3`,`module_list_observe4`,`module_list_observe5`) values (1,'资讯',1,'2014-08-30 00:00:00','2014-08-30 00:00:00',0,'资讯',NULL,NULL,NULL,NULL,NULL);
insert  into `module_list`(`module_list_id`,`module_list_name`,`module_list_status`,`module_list_createtime`,`module_list_lastupdate`,`module_list_parent_id`,`module_list_desc`,`module_list_observe1`,`module_list_observe2`,`module_list_observe3`,`module_list_observe4`,`module_list_observe5`) values (2,'商家',1,'2014-08-30 00:00:00','2014-08-30 00:00:00',0,'商家',NULL,NULL,NULL,NULL,NULL);
insert  into `module_list`(`module_list_id`,`module_list_name`,`module_list_status`,`module_list_createtime`,`module_list_lastupdate`,`module_list_parent_id`,`module_list_desc`,`module_list_observe1`,`module_list_observe2`,`module_list_observe3`,`module_list_observe4`,`module_list_observe5`) values (3,'个人',1,'2014-09-13 16:29:44','2014-09-13 16:29:44',0,'sss',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `order_list` */

DROP TABLE IF EXISTS `order_list`;

CREATE TABLE `order_list` (
  `order_id` int(11) NOT NULL,
  `shop_id` varchar(20) DEFAULT NULL,
  `order_production_id` varchar(500) DEFAULT NULL,
  `order_type` int(11) DEFAULT NULL,
  `order_userid` varchar(20) DEFAULT NULL,
  `order_status` int(11) DEFAULT NULL,
  `order_note` varchar(100) DEFAULT NULL,
  `order_productionamount` int(11) DEFAULT NULL,
  `order_ordertime` date DEFAULT NULL,
  `order_completetime` date DEFAULT NULL,
  `order_lastupdate` date DEFAULT NULL,
  `order_priceeach` decimal(10,2) DEFAULT NULL,
  `order_totalprice` decimal(10,2) DEFAULT NULL,
  `order_discount` decimal(10,2) DEFAULT NULL,
  `order_observe1` varchar(100) DEFAULT NULL,
  `order_observe2` varchar(100) DEFAULT NULL,
  `order_observe3` varchar(100) DEFAULT NULL,
  `order_observe4` varchar(200) DEFAULT NULL,
  `order_observe5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

/*Data for the table `order_list` */

insert  into `order_list`(`order_id`,`shop_id`,`order_production_id`,`order_type`,`order_userid`,`order_status`,`order_note`,`order_productionamount`,`order_ordertime`,`order_completetime`,`order_lastupdate`,`order_priceeach`,`order_totalprice`,`order_discount`,`order_observe1`,`order_observe2`,`order_observe3`,`order_observe4`,`order_observe5`) values (1,'5','1',1,'1',1,'开张第一单',10,'2014-09-08','2014-09-08','2014-09-08','10.00','100.00','10.00',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `production_list` */

DROP TABLE IF EXISTS `production_list`;

CREATE TABLE `production_list` (
  `production_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` char(20) DEFAULT NULL,
  `production_connectid` char(20) DEFAULT NULL,
  `production_name` varchar(50) DEFAULT NULL,
  `production_brandid` varchar(200) DEFAULT NULL,
  `production_color` varchar(100) DEFAULT NULL,
  `production_size` varchar(100) DEFAULT NULL,
  `production_sex` char(10) DEFAULT NULL,
  `production_status` int(11) DEFAULT NULL,
  `production_picture` varchar(1000) DEFAULT NULL,
  `production_icon` varchar(100) DEFAULT NULL,
  `production_notmal_price` char(12) DEFAULT NULL,
  `production_now_price` char(12) DEFAULT NULL,
  `production_brief` varchar(100) DEFAULT NULL,
  `production_description` varchar(1000) DEFAULT NULL,
  `production_storeamount` char(20) DEFAULT NULL,
  `production_salesvolume` char(20) DEFAULT NULL,
  `production_secondtitle` varchar(100) DEFAULT NULL,
  `production_sn` char(100) DEFAULT NULL,
  `production_starttodiscount` datetime DEFAULT NULL,
  `production_timetodiscount` datetime DEFAULT NULL,
  `production_addtime` datetime DEFAULT NULL,
  `production_lastupdate` datetime DEFAULT NULL,
  `production_tobeordered` char(20) DEFAULT NULL,
  `production_webLink` varchar(100) DEFAULT NULL,
  `production_observe1` varchar(100) DEFAULT NULL,
  `production_observe2` varchar(100) DEFAULT NULL,
  `production_observe3` varchar(100) DEFAULT NULL,
  `production_observe4` varchar(200) DEFAULT NULL,
  `production_observe5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`production_id`),
  KEY `FK_PRODUCTI_REFERENCE_SHOP_INF` (`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=gbk;

/*Data for the table `production_list` */

insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (1,'1','1','iPhone6S','苹果','黑色',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (2,'1','1','M1手机','小米','白色',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (3,'1','1','红米Note','小米','白色',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (4,'2','1','iPhone6S66','苹果','黑色',NULL,NULL,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (5,'2','1','M1手机666','小米','白色',NULL,NULL,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (6,'2','1','红米Note666','小米','白色',NULL,NULL,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (7,'2','1','M1手机666','小米','白色',NULL,NULL,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (8,'2','1','红米Note666','小米','白色',NULL,NULL,-1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (11,'3','0','林','林','大','大','',1,'','','','','','','',NULL,'','','2014-09-08 11:14:23','2014-09-08 11:14:23','2014-09-08 11:14:23','2014-09-08 11:14:23','','',NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (12,'4','0','ss','ss','ss','ssx','xxx',-1,'xx','xxx','','','xxx','','',NULL,'','','2014-09-09 21:33:49','2014-09-09 21:33:49','2014-09-09 21:33:49','2014-09-09 21:43:42','','',NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (13,'4','0','xxdddddddddd','xx','xx','xx','xx',-1,'','','','','','','',NULL,'','','2014-09-09 21:53:41','2014-09-09 21:53:41','2014-09-09 21:53:41','2014-09-09 21:53:47','','',NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (14,NULL,'6','手机','华为','黑色','5寸屏',NULL,1,NULL,NULL,NULL,NULL,'CDMA大',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2014-09-15 23:05:20','2014-09-15 23:05:27',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `production_list`(`production_id`,`shop_id`,`production_connectid`,`production_name`,`production_brandid`,`production_color`,`production_size`,`production_sex`,`production_status`,`production_picture`,`production_icon`,`production_notmal_price`,`production_now_price`,`production_brief`,`production_description`,`production_storeamount`,`production_salesvolume`,`production_secondtitle`,`production_sn`,`production_starttodiscount`,`production_timetodiscount`,`production_addtime`,`production_lastupdate`,`production_tobeordered`,`production_webLink`,`production_observe1`,`production_observe2`,`production_observe3`,`production_observe4`,`production_observe5`) values (15,NULL,'7','二手电脑',NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'二手电脑',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2014-09-15 23:31:03','2014-09-15 23:31:54',NULL,'www.person.com.cn',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `shop_account` */

DROP TABLE IF EXISTS `shop_account`;

CREATE TABLE `shop_account` (
  `shop_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_account_name` varchar(50) DEFAULT NULL,
  `shop_phone` char(20) DEFAULT NULL,
  `shop_email` varchar(50) DEFAULT NULL,
  `shop_password` varchar(100) DEFAULT NULL,
  `shop_status` int(11) DEFAULT NULL,
  `shop_type` int(11) DEFAULT NULL,
  `shop_createtime` datetime DEFAULT NULL,
  `shop_online` datetime DEFAULT NULL,
  `shop_name` char(50) DEFAULT NULL,
  `shop_desc` varchar(500) DEFAULT NULL,
  `shop_observe1` varchar(100) DEFAULT NULL,
  `shop_observe2` varchar(100) DEFAULT NULL,
  `shop_observe3` varchar(100) DEFAULT NULL,
  `shop_observe4` varchar(200) DEFAULT NULL,
  `shop_observe5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`shop_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=gbk;

/*Data for the table `shop_account` */

insert  into `shop_account`(`shop_account_id`,`shop_account_name`,`shop_phone`,`shop_email`,`shop_password`,`shop_status`,`shop_type`,`shop_createtime`,`shop_online`,`shop_name`,`shop_desc`,`shop_observe1`,`shop_observe2`,`shop_observe3`,`shop_observe4`,`shop_observe5`) values (1,'chengdushangmao','12345','xx@xx.com',NULL,1,1,'2014-08-30 00:00:00','2014-08-30 00:00:00','四川商贸平台',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_account`(`shop_account_id`,`shop_account_name`,`shop_phone`,`shop_email`,`shop_password`,`shop_status`,`shop_type`,`shop_createtime`,`shop_online`,`shop_name`,`shop_desc`,`shop_observe1`,`shop_observe2`,`shop_observe3`,`shop_observe4`,`shop_observe5`) values (2,'jiudian','23244343','jiudian@jiu.com',NULL,1,1,'2014-08-30 00:00:00','2014-08-30 00:00:00','四川旅游协会',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_account`(`shop_account_id`,`shop_account_name`,`shop_phone`,`shop_email`,`shop_password`,`shop_status`,`shop_type`,`shop_createtime`,`shop_online`,`shop_name`,`shop_desc`,`shop_observe1`,`shop_observe2`,`shop_observe3`,`shop_observe4`,`shop_observe5`) values (3,'app3','','','333333',1,1,'2014-09-13 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_account`(`shop_account_id`,`shop_account_name`,`shop_phone`,`shop_email`,`shop_password`,`shop_status`,`shop_type`,`shop_createtime`,`shop_online`,`shop_name`,`shop_desc`,`shop_observe1`,`shop_observe2`,`shop_observe3`,`shop_observe4`,`shop_observe5`) values (4,'app3','','','333333',1,1,'2014-09-13 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_account`(`shop_account_id`,`shop_account_name`,`shop_phone`,`shop_email`,`shop_password`,`shop_status`,`shop_type`,`shop_createtime`,`shop_online`,`shop_name`,`shop_desc`,`shop_observe1`,`shop_observe2`,`shop_observe3`,`shop_observe4`,`shop_observe5`) values (5,'ss','','','ssssss',-1,1,'2014-09-13 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `shop_info` */

DROP TABLE IF EXISTS `shop_info`;

CREATE TABLE `shop_info` (
  `shop_info_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` char(20) DEFAULT NULL,
  `shop_connect_id` char(20) DEFAULT NULL,
  `shop_info_name` char(50) DEFAULT NULL,
  `shop_info_companyName` char(50) DEFAULT NULL,
  `shop_info_brief` varchar(100) DEFAULT NULL,
  `shop_info_phone` char(20) DEFAULT NULL,
  `shop_info_status` int(10) DEFAULT NULL,
  `shop_info_type` char(20) DEFAULT NULL,
  `shop_info_description` varchar(500) DEFAULT NULL,
  `shop_info_icon` varchar(100) DEFAULT NULL,
  `shop_info_product` varchar(500) DEFAULT NULL,
  `shop_info_address` varchar(200) DEFAULT NULL,
  `shop_info_lon` char(20) DEFAULT NULL,
  `shop_info_lat` char(20) DEFAULT NULL,
  `shop_info_lastupdate` datetime DEFAULT NULL,
  `shop_info_photo_list` varchar(500) DEFAULT NULL,
  `shop_info_createtime` datetime DEFAULT NULL,
  `shop_info_weblink` varchar(200) DEFAULT NULL,
  `shop_info_secondtitle` varchar(100) DEFAULT NULL,
  `shop_info_observe1` varchar(100) DEFAULT NULL,
  `shop_info_observe2` varchar(100) DEFAULT NULL,
  `shop_info_observe3` varchar(100) DEFAULT NULL,
  `shop_info_observe4` varchar(200) DEFAULT NULL,
  `shop_info_observe5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`shop_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=gbk;

/*Data for the table `shop_info` */

insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (1,NULL,'1',NULL,NULL,NULL,'12334343',1,'0','电子类商务平台',NULL,'电子产品','成都青羊区羊大道100号',NULL,NULL,NULL,NULL,'2014-09-07 16:09:59',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (2,NULL,'1',NULL,NULL,NULL,'13546789082',-1,'2','33','33','3','3','3','3','2014-09-08 09:16:19','3','2014-09-07 16:25:57','3',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (3,NULL,'1',NULL,NULL,NULL,'13546789082',-1,'2','33','33','3','3','3','3','2014-09-08 09:16:19','3','2014-09-07 16:25:57','3',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (4,NULL,'1',NULL,NULL,NULL,NULL,1,'2','','',NULL,NULL,NULL,NULL,'2014-09-08 10:45:29',NULL,'2014-09-08 10:45:29','',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (5,'5','5',NULL,NULL,NULL,'13456776543',1,'2','','','','','','','2014-09-13 15:48:37','','2014-09-13 15:48:37','',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (6,'5','10','电子交易平台',NULL,'简介：电子交易平台','22e3434333',1,'2','描述：电子交易平台','/upload/image/20140915/201409152217279.png',NULL,'青羊区',NULL,NULL,'2014-09-15 22:23:22',NULL,'2014-09-15 22:17:49',NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_info`(`shop_info_id`,`shop_id`,`shop_connect_id`,`shop_info_name`,`shop_info_companyName`,`shop_info_brief`,`shop_info_phone`,`shop_info_status`,`shop_info_type`,`shop_info_description`,`shop_info_icon`,`shop_info_product`,`shop_info_address`,`shop_info_lon`,`shop_info_lat`,`shop_info_lastupdate`,`shop_info_photo_list`,`shop_info_createtime`,`shop_info_weblink`,`shop_info_secondtitle`,`shop_info_observe1`,`shop_info_observe2`,`shop_info_observe3`,`shop_info_observe4`,`shop_info_observe5`) values (7,'5','11','张三','**公司','研发人员','12342',1,'2','JAVA','/upload/image/20140915/20140915232269.png',NULL,'青羊区',NULL,NULL,'2014-09-15 23:23:19',NULL,'2014-09-15 23:22:43',NULL,'##学校',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `shop_module` */

DROP TABLE IF EXISTS `shop_module`;

CREATE TABLE `shop_module` (
  `shop_module_id` int(11) NOT NULL AUTO_INCREMENT,
  `shop_id` char(20) DEFAULT NULL,
  `shop_module_list_id` char(20) DEFAULT NULL,
  `shop_module_list_name` char(20) DEFAULT NULL,
  `shop_module_createtime` datetime DEFAULT NULL,
  `shop_module_lastupdate` datetime DEFAULT NULL,
  `shop_module_status` char(10) DEFAULT NULL,
  `shop_module_desc` varchar(100) DEFAULT NULL,
  `shop_module_observe1` varchar(200) DEFAULT NULL,
  `shop_module_observe2` varchar(200) DEFAULT NULL,
  `shop_module_observe3` varchar(200) DEFAULT NULL,
  `shop_module_observe4` varchar(200) DEFAULT NULL,
  `shop_module_observe5` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`shop_module_id`),
  KEY `FK_SHOP_MOD_REFERENCE_SHOP_ACC` (`shop_id`),
  KEY `FK_SHOP_MOD_REFERENCE_MODULE_L` (`shop_module_list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=gbk;

/*Data for the table `shop_module` */

insert  into `shop_module`(`shop_module_id`,`shop_id`,`shop_module_list_id`,`shop_module_list_name`,`shop_module_createtime`,`shop_module_lastupdate`,`shop_module_status`,`shop_module_desc`,`shop_module_observe1`,`shop_module_observe2`,`shop_module_observe3`,`shop_module_observe4`,`shop_module_observe5`) values (1,'1','1','新闻资讯','2014-08-30 00:00:00','2014-08-30 00:00:00','1','这是一家公司',NULL,NULL,NULL,NULL,NULL);
insert  into `shop_module`(`shop_module_id`,`shop_id`,`shop_module_list_id`,`shop_module_list_name`,`shop_module_createtime`,`shop_module_lastupdate`,`shop_module_status`,`shop_module_desc`,`shop_module_observe1`,`shop_module_observe2`,`shop_module_observe3`,`shop_module_observe4`,`shop_module_observe5`) values (2,'1','2','商家信息','2014-08-30 00:00:00','2014-08-30 00:00:00','1',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_module`(`shop_module_id`,`shop_id`,`shop_module_list_id`,`shop_module_list_name`,`shop_module_createtime`,`shop_module_lastupdate`,`shop_module_status`,`shop_module_desc`,`shop_module_observe1`,`shop_module_observe2`,`shop_module_observe3`,`shop_module_observe4`,`shop_module_observe5`) values (5,'5','1','新闻资讯','2014-09-13 14:13:39','2014-09-13 15:15:26','1',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_module`(`shop_module_id`,`shop_id`,`shop_module_list_id`,`shop_module_list_name`,`shop_module_createtime`,`shop_module_lastupdate`,`shop_module_status`,`shop_module_desc`,`shop_module_observe1`,`shop_module_observe2`,`shop_module_observe3`,`shop_module_observe4`,`shop_module_observe5`) values (10,'5','2','商家信息','2014-09-13 14:39:02','2014-09-13 15:15:26','1',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `shop_module`(`shop_module_id`,`shop_id`,`shop_module_list_id`,`shop_module_list_name`,`shop_module_createtime`,`shop_module_lastupdate`,`shop_module_status`,`shop_module_desc`,`shop_module_observe1`,`shop_module_observe2`,`shop_module_observe3`,`shop_module_observe4`,`shop_module_observe5`) values (11,'5','3','个人信息','2014-09-13 14:39:02','2014-09-13 15:15:26','1',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `user_account` */

DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `user_account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_shopid` int(11) DEFAULT NULL,
  `user_account_name` varchar(50) NOT NULL,
  `user_phone` char(20) DEFAULT NULL,
  `user_email` varchar(50) DEFAULT NULL,
  `user_password` varchar(50) DEFAULT NULL,
  `user_sendcode` varchar(50) DEFAULT NULL,
  `user_status` int(11) DEFAULT NULL,
  `user_type` int(11) DEFAULT NULL,
  `user_createtime` datetime DEFAULT NULL,
  `user_online` char(20) DEFAULT NULL,
  `user_logintime` datetime DEFAULT NULL,
  `user_offtime` datetime DEFAULT NULL,
  `user_observe1` varchar(100) DEFAULT NULL,
  `user_observe2` varchar(100) DEFAULT NULL,
  `user_observe3` varchar(100) DEFAULT NULL,
  `user_observe4` varchar(200) DEFAULT NULL,
  `user_observe5` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=gbk;

/*Data for the table `user_account` */

insert  into `user_account`(`user_account_id`,`user_shopid`,`user_account_name`,`user_phone`,`user_email`,`user_password`,`user_sendcode`,`user_status`,`user_type`,`user_createtime`,`user_online`,`user_logintime`,`user_offtime`,`user_observe1`,`user_observe2`,`user_observe3`,`user_observe4`,`user_observe5`) values (1,0,'admin','028-000222333','4224@qq.com','123456','5dfefdfdffdas',1,0,'2014-08-03 00:00:00','1','2014-08-03 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `user_account`(`user_account_id`,`user_shopid`,`user_account_name`,`user_phone`,`user_email`,`user_password`,`user_sendcode`,`user_status`,`user_type`,`user_createtime`,`user_online`,`user_logintime`,`user_offtime`,`user_observe1`,`user_observe2`,`user_observe3`,`user_observe4`,`user_observe5`) values (3,1,'user1','13456890765','111@qq.com','5ed9c282142c7629',NULL,1,2,'2014-08-03 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `user_account`(`user_account_id`,`user_shopid`,`user_account_name`,`user_phone`,`user_email`,`user_password`,`user_sendcode`,`user_status`,`user_type`,`user_createtime`,`user_online`,`user_logintime`,`user_offtime`,`user_observe1`,`user_observe2`,`user_observe3`,`user_observe4`,`user_observe5`) values (4,1,'user2','13456789040','2@qq.com','2aa6c793b1c9ad34',NULL,-2,2,'2014-08-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `user_account`(`user_account_id`,`user_shopid`,`user_account_name`,`user_phone`,`user_email`,`user_password`,`user_sendcode`,`user_status`,`user_type`,`user_createtime`,`user_online`,`user_logintime`,`user_offtime`,`user_observe1`,`user_observe2`,`user_observe3`,`user_observe4`,`user_observe5`) values (5,5,'app1','13689097656','app1@ss.com','123456',NULL,1,1,'2014-08-31 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `user_account`(`user_account_id`,`user_shopid`,`user_account_name`,`user_phone`,`user_email`,`user_password`,`user_sendcode`,`user_status`,`user_type`,`user_createtime`,`user_online`,`user_logintime`,`user_offtime`,`user_observe1`,`user_observe2`,`user_observe3`,`user_observe4`,`user_observe5`) values (6,5,'xxx',',1,2,3,4,5,6,7,8',NULL,NULL,NULL,1,2,'2014-09-07 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
insert  into `user_account`(`user_account_id`,`user_shopid`,`user_account_name`,`user_phone`,`user_email`,`user_password`,`user_sendcode`,`user_status`,`user_type`,`user_createtime`,`user_online`,`user_logintime`,`user_offtime`,`user_observe1`,`user_observe2`,`user_observe3`,`user_observe4`,`user_observe5`) values (9,5,'ss','13567890043','222@ss.com','3ccd17be63902c55',NULL,1,1,'2014-09-13 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
