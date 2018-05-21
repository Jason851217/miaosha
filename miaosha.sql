CREATE TABLE `miaosha_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id，手机号',
  `nickname` varchar(30) COLLATE utf8_german2_ci NOT NULL COMMENT '用户昵称',
  `pwd` varchar(32) COLLATE utf8_german2_ci NOT NULL COMMENT '用户密码,md5(md5(明文密码，固定salt)，随机salt)',
  `salt` varchar(10) COLLATE utf8_german2_ci DEFAULT NULL COMMENT '盐值',
  `head` varchar(128) COLLATE utf8_german2_ci DEFAULT NULL COMMENT '头像，云存储ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `login_count` int(11) DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nick_name_idx` (`nickname`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13407169159 DEFAULT CHARSET=utf8 COLLATE=utf8_german2_ci;

-- 测试的明文密码：1qaz2wsx
INSERT INTO `miaosha_user` VALUES (13407169158, 'Jason', '1d16ac12a3ddddca1c20ab5c0fd60298', 'baeeae', NULL, NULL, NULL, 0);


-- 商品表
CREATE TABLE `goods` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) DEFAULT NULL COMMENT '商品图片',
  `goods_detail` longtext COMMENT '商品描述',
  `goods_price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
  `goods_stock` int(11) DEFAULT '0' COMMENT '商品库存',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 秒杀商品表(单独创建秒杀商品表的目的是：如果我们直接在商品表里面添加字段来标示是秒杀商品的话，根据业务的发展可能不定期的就会进行大促，商品表不定期就会修改表结构，所以我们索性单独出来)
CREATE TABLE `miaosha_goods` (
  `id` bigint(20) NOT NULL COMMENT '秒杀id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '秒杀商品id(外键：指向商品id)',
  `miaosha_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀价格',
  `stock_count` int(11) DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 订单信息表
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `delivery_addr_id` bigint(20) DEFAULT NULL COMMENT '收获地址id',
  `goods_name` varchar(16) DEFAULT NULL COMMENT '订单商品名称(冗余过来的商品名称)',
  `goods_count` int(11) DEFAULT NULL COMMENT '订单商品数量',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '订单商品价格',
  `order_channel` tinyint(4) DEFAULT NULL COMMENT '订单渠道(1.pc 2.android 3.ios)',
  `status` tinyint(4) DEFAULT NULL COMMENT '订单状态(0:新建未支付 1:已支付 2:已发货 3:已收货 4:已退款 5.已完成)',
  `create_date` datetime DEFAULT NULL COMMENT '下单时间',
  `pay_date` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 秒杀订单表(主要记录，哪个用户秒杀了哪个商品)
CREATE TABLE `miaosha_order` (
  `id` bigint(20) NOT NULL COMMENT '秒杀订单id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;