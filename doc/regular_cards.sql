# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 172.21.120.221 (MySQL 10.1.19-MariaDB)
# Database: landlords
# Generation Time: 2017-06-02 08:34:28 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table regular_cards_17
# ------------------------------------------------------------

DROP TABLE IF EXISTS `regular_cards_17`;

CREATE TABLE `regular_cards_17` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `c_center` varchar(64) DEFAULT NULL COMMENT '地主牌',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常规赛，首次发牌时地主手牌,此牌库随机获取';



# Dump of table regular_cards_37
# ------------------------------------------------------------

DROP TABLE IF EXISTS `regular_cards_37`;

CREATE TABLE `regular_cards_34` (
  `id` varchar(50) NOT NULL DEFAULT '',
  `center_id` varchar(50) DEFAULT NULL COMMENT '地主牌id',
  `c_under` varchar(64) DEFAULT NULL COMMENT '底牌',
  `c_right` varchar(64) DEFAULT NULL COMMENT '右手边机器人手牌',
  `c_left` varchar(64) DEFAULT NULL COMMENT '左手边机器人手牌',
  `prize_level` int(11) DEFAULT NULL COMMENT '奖等,1->一等奖，2->二等奖，3->三等奖,4->四等奖A，5->未中奖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='常规赛，加倍后发牌,机器人手牌和底牌，根据奖等及地主手牌id查询';




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
