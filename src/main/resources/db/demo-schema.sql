/*
Navicat MySQL Data Transfer

Source Server         : 169.24.2.40
Source Server Version : 50152
Source Host           : 169.24.2.40:3306
Source Database       : activiti6_test

Target Server Type    : MYSQL
Target Server Version : 50152
File Encoding         : 65001

Date: 2018-01-16 14:59:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_application
-- ----------------------------
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
  `id` varchar(32) NOT NULL,
  `form_id` varchar(50) DEFAULT NULL,
  `project_cd` varchar(50) DEFAULT NULL,
  `project_name` varchar(255) DEFAULT NULL,
  `requirement` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
