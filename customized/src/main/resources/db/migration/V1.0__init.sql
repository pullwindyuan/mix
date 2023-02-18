--
-- 表的结构 `auth_user`
--
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
                           `id` varchar(64) NOT NULL COMMENT 'id',
                           `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
                           `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
                           `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '姓名',
                           `phone` varchar(20) NOT NULL COMMENT '手机号码',
                           `user_status` tinyint(3) UNSIGNED DEFAULT '0' COMMENT '用户状态 0--正常 1--禁用',
                           `department` varchar(64) NOT NULL COMMENT '部门id',
                           `position` varchar(64) NOT NULL COMMENT '职位ID',
                           `role` varchar(255) DEFAULT '',
                           `avatar` varchar(255) DEFAULT '' COMMENT '头像',
                           `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           `del_status` tinyint(1) DEFAULT '0' COMMENT '软删除标志   1-是 0-否 ',
                           `created_by` varchar(64) DEFAULT '' COMMENT  '创建人',
                           `updated_by` varchar(64) DEFAULT '' COMMENT '修改人',
                           PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- 转存表中的数据 `auth_user`
--

INSERT INTO `auth_user` (`id`, `email`, `password`, `name`, `phone`, `user_status`, `department`, `position`, `role`, `avatar`, `created_at`, `updated_at`, `del_status`, `created_by`, `updated_by`) VALUES
('abc', 'admin', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', '管理用户', '13800138000', 0, 'abc', 'abc', 'ADMIN', 'https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png', '2018-12-30 04:02:58', '2019-04-22 16:40:15', 0, 0, 0);

-- ----------------------------
-- Records of cfg_function
-- ----------------------------
DROP TABLE IF EXISTS `cfg_function`;
CREATE TABLE `cfg_function` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `parent_id` varchar(64) DEFAULT '' COMMENT '父ID',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能名称',
  `method_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法类型',
  `method_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法名称',
  `url` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'url',
  `level` tinyint(1) DEFAULT '0' COMMENT '级别',
  `del_status` tinyint(1) DEFAULT '0' COMMENT '软删除标志   1-是 0-否 ',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `role_desc` varchar(20) DEFAULT '' NULL COMMENT '角色描述',
  `role_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `del_status` tinyint(1) DEFAULT '0' COMMENT '软删除标志   1-是 0-否 ',
  `created_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT '' COMMENT '修改人',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
INSERT INTO `auth_role` VALUES ('abc', 'abc', 'admin', '2019-10-24 17:53:59', '2019-10-24 17:53:59', '0', '2103', '2103', '');

CREATE TABLE IF NOT EXISTS  `auth_user_role` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `role_id` varchar(64) DEFAULT '0' COMMENT '角色ID',
  `user_id` varchar(64) DEFAULT '0' COMMENT '用户ID',
  `del_status` tinyint(1) DEFAULT '0' COMMENT '软删除标志   1-是 0-否 ',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(64) NOT NULL COMMENT '创建人',
  `updated_by` varchar(64) NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `cfg_function` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `parent_id` varchar(64) DEFAULT '0' COMMENT '父ID',
  `level` tinyint(1) DEFAULT '0'  COMMENT '级别',
  `name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能名称',
  `method_type` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法类型',
  `method_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '方法名称',
  `url` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'url',
  `del_status` tinyint(1) DEFAULT '0' COMMENT '软删除标志   1-是 0-否 ',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(64) DEFAULT '0' COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `auth_role_function` (
  `id` varchar(64) NOT NULL COMMENT 'ID',
  `role_id` varchar(64) DEFAULT '0' COMMENT '父ID',
  `role_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色名',
  `function_name` varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '功能名称',
  `function_id` int(11) DEFAULT '0' COMMENT '功能ID',
  `del_status` tinyint(1) DEFAULT '0' COMMENT '软删除标志   1-是 0-否 ',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `created_by` varchar(64) DEFAULT '0' COMMENT '创建人',
  `updated_by` varchar(64) DEFAULT '0' COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;


