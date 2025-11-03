/*
 Navicat Premium Dump SQL

 Source Server         : myblog
 Source Server Type    : MySQL
 Source Server Version : 50744 (5.7.44)
 Source Host           : 127.0.0.1:3306
 Source Schema         : myblog

 Target Server Type    : MySQL
 Target Server Version : 50744 (5.7.44)
 File Encoding         : 65001

 Date: 03/11/2025 11:03:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blog_friend
-- ----------------------------
DROP TABLE IF EXISTS `blog_friend`;
CREATE TABLE `blog_friend` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                               `blog_address` varchar(50) NOT NULL COMMENT '博客地址',
                               `blog_name` varchar(50) NOT NULL COMMENT '博客名称',
                               `head_img` varchar(255) NOT NULL COMMENT '头像',
                               `blog_img` varchar(255) DEFAULT NULL COMMENT '博客图片',
                               `description` varchar(255) DEFAULT NULL COMMENT '语录',
                               `is_status` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '状态',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `is_deleted` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '逻辑删除标识',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1789597155236048898 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='友链表';

-- ----------------------------
-- Table structure for blog_message
-- ----------------------------
DROP TABLE IF EXISTS `blog_message`;
CREATE TABLE `blog_message` (
                                `id` int(11) NOT NULL AUTO_INCREMENT,
                                `content` varchar(255) DEFAULT NULL,
                                `flag` tinyint(1) unsigned zerofill DEFAULT '0',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for blog_music
-- ----------------------------
DROP TABLE IF EXISTS `blog_music`;
CREATE TABLE `blog_music` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `music_name` varchar(20) DEFAULT NULL COMMENT '音乐名称',
                              `music_code` varchar(20) DEFAULT NULL COMMENT '音乐编号',
                              `music_path` varchar(255) DEFAULT NULL COMMENT '音乐地址',
                              `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                              `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10007 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='音乐表';

-- ----------------------------
-- Table structure for blog_picture
-- ----------------------------
DROP TABLE IF EXISTS `blog_picture`;
CREATE TABLE `blog_picture` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图片主键',
                                `picture_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
                                `picture_address` varchar(255) DEFAULT NULL COMMENT '图片地址',
                                `picture_description` varchar(255) DEFAULT NULL COMMENT '图片内容',
                                `picture_time` varchar(255) DEFAULT NULL COMMENT '图片时间',
                                `picture_level` varchar(255) DEFAULT NULL COMMENT '级别',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='相册';

-- ----------------------------
-- Table structure for blog_upload
-- ----------------------------
DROP TABLE IF EXISTS `blog_upload`;
CREATE TABLE `blog_upload` (
                               `id` bigint(20) NOT NULL COMMENT '主键',
                               `picture_address` varchar(255) DEFAULT NULL COMMENT '图片地址',
                               `picture_description` varchar(255) DEFAULT NULL COMMENT '图片内容',
                               `picture_name` varchar(255) DEFAULT NULL COMMENT '图片名称',
                               `picture_time` varchar(255) DEFAULT NULL COMMENT '图片时间',
                               `picture_path` varchar(255) DEFAULT NULL COMMENT '图片地址',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='相册';

-- ----------------------------
-- Table structure for blog_user
-- ----------------------------
DROP TABLE IF EXISTS `blog_user`;
CREATE TABLE `blog_user` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                             `type` bigint(20) DEFAULT NULL COMMENT '类型',
                             `username` varchar(50) DEFAULT NULL COMMENT '账号',
                             `password` varchar(50) DEFAULT NULL COMMENT '密码',
                             `avatar` varchar(50) DEFAULT NULL COMMENT '化名',
                             `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
                             `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Table structure for mt_comments
-- ----------------------------
DROP TABLE IF EXISTS `mt_comments`;
CREATE TABLE `mt_comments` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `wall_id` bigint(20) NOT NULL COMMENT '墙留言ID',
                               `user_id` varchar(100) NOT NULL COMMENT '评论者ID',
                               `img_url` varchar(100) DEFAULT '' COMMENT '头像路径',
                               `comment` varchar(1000) DEFAULT NULL COMMENT '评论内容',
                               `name` varchar(100) NOT NULL COMMENT '用户名',
                               `moment` varchar(100) NOT NULL COMMENT '时间',
                               `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1985174364394364930 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for mt_feedbacks
-- ----------------------------
DROP TABLE IF EXISTS `mt_feedbacks`;
CREATE TABLE `mt_feedbacks` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `wall_id` bigint(20) NOT NULL COMMENT '墙留言ID',
                                `user_id` varchar(100) NOT NULL COMMENT '反馈者ID',
                                `type` int(11) NOT NULL COMMENT '反馈类型0喜欢1举报2撤销',
                                `moment` varchar(100) NOT NULL COMMENT '时间',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1814671202617458691 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for mt_walls
-- ----------------------------
DROP TABLE IF EXISTS `mt_walls`;
CREATE TABLE `mt_walls` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `type` int(11) NOT NULL COMMENT '类型0信息1图片',
                            `message` varchar(1000) DEFAULT NULL COMMENT '留言',
                            `name` varchar(100) NOT NULL COMMENT '用户名',
                            `user_id` varchar(100) NOT NULL COMMENT '创建者ID',
                            `moment` varchar(100) NOT NULL COMMENT '时间',
                            `label` int(11) NOT NULL COMMENT '标签',
                            `color` int(11) DEFAULT NULL COMMENT '颜色',
                            `img_url` varchar(255) DEFAULT '' COMMENT '图片路径',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1985174347717812227 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `rid` bigint(20) DEFAULT NULL COMMENT '角色id',
                             `nickname` varchar(20) DEFAULT NULL COMMENT '真实名称',
                             `username` varchar(50) NOT NULL COMMENT '账号',
                             `password` varchar(32) NOT NULL COMMENT '密码',
                             `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
                             `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                             `is_status` tinyint(1) unsigned zerofill NOT NULL COMMENT '状态',
                             `token` varchar(255) NOT NULL COMMENT '令牌',
                             `last_login_address` varchar(255) DEFAULT NULL COMMENT '上次登录地址',
                             `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             `create_by` varchar(32) DEFAULT NULL COMMENT '创建人',
                             `update_by` varchar(32) DEFAULT NULL COMMENT '更新人',
                             `is_deleted` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '逻辑删除标识',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_attendance
-- ----------------------------
DROP TABLE IF EXISTS `sys_attendance`;
CREATE TABLE `sys_attendance` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                  `uid` bigint(20) NOT NULL COMMENT '用户id',
                                  `name` varchar(50) NOT NULL COMMENT '姓名',
                                  `type` varchar(50) NOT NULL COMMENT '类型',
                                  `length` varchar(50) NOT NULL COMMENT '时长',
                                  `date` datetime NOT NULL COMMENT '迟到时间',
                                  `remark` varchar(100) NOT NULL COMMENT '原因',
                                  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_report
-- ----------------------------
DROP TABLE IF EXISTS `sys_report`;
CREATE TABLE `sys_report` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `count` bigint(20) DEFAULT NULL COMMENT '用户数',
                              `academy` varchar(50) DEFAULT NULL COMMENT '学院',
                              `date` datetime DEFAULT NULL COMMENT '时间',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_rights
-- ----------------------------
DROP TABLE IF EXISTS `sys_rights`;
CREATE TABLE `sys_rights` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `rid` bigint(20) DEFAULT NULL COMMENT '角色id',
                              `auth_name` varchar(20) NOT NULL COMMENT '权限名称',
                              `level` int(11) NOT NULL COMMENT '级别',
                              `path` varchar(20) NOT NULL COMMENT '路径',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
                            `id` bigint(20) NOT NULL COMMENT '主键',
                            `rights_id` bigint(20) DEFAULT NULL COMMENT '权限id',
                            `role_name` varchar(20) NOT NULL COMMENT '角色名称',
                            `role_desc` varchar(30) NOT NULL COMMENT '角色介绍',
                            `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                            PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_article_type
-- ----------------------------
DROP TABLE IF EXISTS `t_article_type`;
CREATE TABLE `t_article_type` (
                                  `article_id` tinyint(1) NOT NULL AUTO_INCREMENT COMMENT 'id',
                                  `article_type` varchar(20) NOT NULL COMMENT '文章类型\r\n',
                                  PRIMARY KEY (`article_id`) USING BTREE,
                                  KEY `name` (`article_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='分类';

-- ----------------------------
-- Table structure for t_blog
-- ----------------------------
DROP TABLE IF EXISTS `t_blog`;
CREATE TABLE `t_blog` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客id',
                          `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                          `type_id` bigint(20) DEFAULT NULL COMMENT '分类id',
                          `title` varchar(255) DEFAULT NULL COMMENT '标题',
                          `content` longtext COMMENT '内容',
                          `description` varchar(255) NOT NULL COMMENT '描述',
                          `first_picture` varchar(255) DEFAULT NULL COMMENT '图片路径',
                          `published` tinyint(1) NOT NULL COMMENT '公开',
                          `appreciation` tinyint(1) NOT NULL COMMENT '赞赏',
                          `comment` tinyint(1) NOT NULL COMMENT '评论',
                          `recommend` tinyint(1) NOT NULL COMMENT '推荐',
                          `share_statement` tinyint(1) NOT NULL COMMENT '转载声明',
                          `top` tinyint(1) DEFAULT '0' COMMENT '置顶',
                          `flag` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '文章类型',
                          `views` bigint(20) DEFAULT '50' COMMENT '访问次数',
                          `comment_count` bigint(20) DEFAULT NULL COMMENT '评论次数',
                          `special` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '特殊操作(0.不做任何操作，1.开启链接)',
                          `special_content` varchar(255) DEFAULT NULL COMMENT '特殊操作内容',
                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                          `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1833069315431763971 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='博文';

-- ----------------------------
-- Table structure for t_blog_copy1
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_copy1`;
CREATE TABLE `t_blog_copy1` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '博客id',
                                `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                                `type_id` bigint(20) DEFAULT NULL COMMENT '分类id',
                                `title` varchar(255) DEFAULT NULL COMMENT '标题',
                                `content` longtext COMMENT '内容',
                                `description` varchar(255) NOT NULL COMMENT '描述',
                                `first_picture` varchar(255) DEFAULT NULL COMMENT '图片路径',
                                `published` tinyint(1) NOT NULL COMMENT '公开',
                                `appreciation` tinyint(1) NOT NULL COMMENT '赞赏',
                                `comment` tinyint(1) NOT NULL COMMENT '评论',
                                `recommend` tinyint(1) NOT NULL COMMENT '推荐',
                                `share_statement` tinyint(1) NOT NULL COMMENT '转载声明',
                                `top` tinyint(1) DEFAULT '0' COMMENT '置顶',
                                `flag` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '文章类型',
                                `views` bigint(20) DEFAULT '50' COMMENT '访问次数',
                                `comment_count` bigint(20) DEFAULT NULL COMMENT '评论次数',
                                `special` tinyint(1) unsigned zerofill DEFAULT '0' COMMENT '特殊操作(0.不做任何操作，1.开启链接)',
                                `special_content` varchar(255) DEFAULT NULL COMMENT '特殊操作内容',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                                `is_deleted` tinyint(1) DEFAULT NULL COMMENT '逻辑删除标识',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1833069315431763971 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='博文';

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论主键',
                             `blog_id` bigint(20) DEFAULT NULL COMMENT '博客id',
                             `parent_comment_id` bigint(20) DEFAULT NULL COMMENT '父id',
                             `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
                             `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
                             `content` varchar(255) DEFAULT NULL COMMENT '内容',
                             `avatar` varchar(255) DEFAULT NULL COMMENT '化名',
                             `admin_comment` bit(1) NOT NULL,
                             `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='评论表';

-- ----------------------------
-- Table structure for t_message
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
                             `id` int(20) NOT NULL AUTO_INCREMENT,
                             `nickname` varchar(255) DEFAULT NULL,
                             `email` varchar(255) DEFAULT NULL,
                             `content` varchar(255) DEFAULT NULL,
                             `avatar` varchar(255) DEFAULT NULL,
                             `create_time` datetime DEFAULT NULL,
                             `parent_message_id` bigint(20) DEFAULT NULL,
                             `admin_message` bit(1) NOT NULL,
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag` (
                         `id` bigint(20) NOT NULL COMMENT 'id',
                         `name` varchar(20) NOT NULL COMMENT '标签名',
                         PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='标签';

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
                          `name` varchar(20) NOT NULL COMMENT '类别名',
                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                          `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                          `is_deleted` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '逻辑删除标识',
                          PRIMARY KEY (`id`) USING BTREE,
                          KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10011 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='分类';

-- ----------------------------
-- Table structure for tag_blog_rel
-- ----------------------------
DROP TABLE IF EXISTS `tag_blog_rel`;
CREATE TABLE `tag_blog_rel` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `tid` bigint(20) NOT NULL,
                                `bid` bigint(20) NOT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                KEY `tid` (`bid`,`tid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1833076550700965890 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
