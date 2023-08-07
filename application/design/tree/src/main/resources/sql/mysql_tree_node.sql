-- @formatter:off
drop table if exists tree_node;
create table tree_node
(
    `id`          bigint(20) unsigned not null auto_increment comment 'id',
    `name`        varchar(1024) not null default 'default' comment '名称',
    `lft`         int           not null comment 'left value',
    `rgt`         int           not null comment 'right value',
    `level`       int           not null comment 'level value',
    `sort_key`    int           not null default 0 comment 'sort key',
    `create_time` datetime      not null DEFAULT CURRENT_TIMESTAMP comment 'create_time',
    `update_time` datetime      not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment 'update_time',
    primary key (`id`)

) engine = InnoDB
  ROW_FORMAT = DYNAMIC comment = 'tree node';
-- @formatter:on