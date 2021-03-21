/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : ljw

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2021-03-20 08:13:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` varchar(32) DEFAULT '0' COMMENT '父级菜单ID',
  `name` varchar(32) DEFAULT NULL COMMENT '菜单名称',
  `type` char(1) DEFAULT NULL COMMENT '菜单类型 0：菜单 | 1：按钮（权限）',
  `permission` varchar(32) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `path` varchar(32) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(32) DEFAULT NULL COMMENT '组件路径',
  `sort` varchar(32) DEFAULT NULL COMMENT '排序',
  `hidden` varchar(1) DEFAULT '0' COMMENT '是否显示 0：显示 | 1：隐藏',
  `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(32) DEFAULT NULL COMMENT '更新时间',
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', '1', null, null, null, null, null, '1', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('2', '1', '菜单管理', '1', null, null, '/menu', 'sys/menu/menu', null, '1', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('3', '1', '角色管理', '1', null, null, '/role', 'sys/role/role', null, '1', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('4', '1', '用户管理', '1', null, null, '/user', 'sys/user/user', null, '1', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('5', '2', '添加', '2', 'sys:menu:add', null, null, null, null, '1', null, null, null, null, null);
INSERT INTO `sys_menu` VALUES ('6', '2', '修改', '2', 'sys:menu:edit', null, null, null, null, '1', null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员');
INSERT INTO `sys_role` VALUES ('2', '普通用户');
INSERT INTO `sys_role` VALUES ('3', '管理员1');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '4');
INSERT INTO `sys_role_menu` VALUES ('1', '3');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '6');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `openid` varchar(255) DEFAULT NULL,
  `create_time` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `last_login_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '58444ab1bfbb93cc6f053b168f50fdd3', null, null, null, '0', null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('2', 'test', '58444ab1bfbb93cc6f053b168f50fdd3', null, null, null, '0', null, null, null, null, null);
INSERT INTO `sys_user` VALUES ('3', '是我呀~?', '79af09c71ec850dd6c69b3486175f595', '了', null, null, '0', 'oypcC1i6fSXlQfyPiPvNVK7mhFNc', '2020-11-3 11:20:07', null, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLNRjB8ZwrDr0LDrBcd2CfVINmaqibw1auOfblNAaq44tGRJlJRJaUat8qckO1abIcmvucK8HuwuOQ/132', '2020-11-4 8:38:50');
INSERT INTO `sys_user` VALUES ('5', 'ljw', '58444ab1bfbb93cc6f053b168f50fdd3', '超级管理员', null, null, '0', null, null, null, null, null);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1');
