drop
database if exists `seata-business`;
drop
database if exists `seata-order`;
drop
database if exists `seata-points`;
drop
database if exists `seata-storage`;


create
database `seata-business`;
create
database `seata-order`;
create
database `seata-points`;
create
database `seata-storage`;


use
`seata-order`;

DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `rollback_info` longblob                                                NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime(0) NOT NULL,
    `log_modified`  datetime(0) NOT NULL,
    `ext`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ux_undo_log` (`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `order_id`  int(255) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
    `username`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
    `points`    int(255) NOT NULL COMMENT '增加会员积分',
    `goodsCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品编码',
    `quantity`  int(255) NOT NULL COMMENT '购买数量',
    `amount`    float(255, 0
) NOT NULL COMMENT '物品总价',
    PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 51
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;



use
`seata-points`;

DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `rollback_info` longblob                                                NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime(0) NOT NULL,
    `log_modified`  datetime(0) NOT NULL,
    `ext`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ux_undo_log` (`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `points`;
CREATE TABLE `points`
(
    `points_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
    `username`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员编号',
    `quantity`  int(255) NOT NULL COMMENT '积分数量',
    PRIMARY KEY (`points_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

INSERT INTO `points`
VALUES (1, 'zhangsan', 100);


use
`seata-storage`;

DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `context`       varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `rollback_info` longblob                                                NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime(0) NOT NULL,
    `log_modified`  datetime(0) NOT NULL,
    `ext`           varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `ux_undo_log` (`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`
(
    `storage_id` int(11) NOT NULL AUTO_INCREMENT,
    `goods_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
    `quantity`   int(255) NOT NULL,
    PRIMARY KEY (`storage_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 2
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of storage
-- ----------------------------
INSERT INTO `storage`
VALUES (1, 'coke', 100);