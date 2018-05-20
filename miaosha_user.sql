CREATE TABLE `miaosha`.`无标题`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id，手机号',
  `nickname` varchar(30) CHARACTER SET utf8 COLLATE utf8_german2_ci NOT NULL COMMENT '用户昵称',
  `pwd` varchar(32) CHARACTER SET utf8 COLLATE utf8_german2_ci NOT NULL COMMENT '用户密码,md5(md5(明文密码，固定salt)，随机salt)',
  `salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_german2_ci NULL DEFAULT NULL COMMENT '盐值',
  `head` varchar(128) CHARACTER SET utf8 COLLATE utf8_german2_ci NULL DEFAULT NULL COMMENT '头像，云存储ID',
  `register_date` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `login_count` int(11) NULL DEFAULT 0 COMMENT '登录次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `nick_name_idx`(`nickname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_german2_ci ROW_FORMAT = Compact;