-- liquibase formatted sql

-- changeset zhuzhenjie:20221117001
-- precondition-sql-check expectedResult:0 select count(table_name) from information_schema.tables where table_schema = 'springboot' and table_name = 'tree_node'
create table tree_node
(
    `id`       bigint(20) unsigned not null auto_increment comment 'id',
    `name`     varchar(1024)       not null default 'default' comment '名称',
    `lft`      int                 not null comment 'left value',
    `rgt`      int                 not null comment 'right value',
    `level`    int                 not null comment 'level value',
    `sort_key` int                 not null default 0 comment 'sort key',
    primary key (`id`)
) engine = InnoDB
  row_format = dynamic comment = 'tree node';

-- changeset zhuzhenjie:20221117002
alter table tree_node
    add column `create_time` datetime not null default now() comment 'create_time';
alter table tree_node
    add column `update_time` datetime not null default now() on update now() comment 'update_time';