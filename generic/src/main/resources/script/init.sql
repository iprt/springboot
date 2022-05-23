drop table if exists request_log;

create table request_log
(
    `id`           bigint       not null auto_increment comment 'id',
    `request_time` datetime     not null default now() comment 'create time',
    `application`  varchar(30)  not null default '' comment 'application name',
    `uname`        varchar(50)  not null default '' comment 'user name',
    `uid`          bigint       not null default -1 comment 'user name',
    `uri`          varchar(100) not null default '' comment 'request uri',
    `success`      tinyint(1) not null default 0 comment 'success',
    `method`       varchar(10)  not null default 'GET' comment 'method',
    `content_type` varchar(30)  not null default '' comment 'content_type',
    `request_desc` varchar(1024)  not null default '' comment 'request desc',
    `query_string` text comment 'query string',
    `body`         text comment 'request body',

    `ip`           varchar(20)  not null default '127.0.0.1' comment 'ip',
    `user_agent`   varchar(255) not null default '' comment 'user agent',

    primary key (`id`)
)engine = InnoDB ROW_FORMAT = DYNAMIC comment = '请求记录';


drop table if exists `dept`;

create table `dept`
(
    `id`   varchar(10) not null comment 'id',
    `name` varchar(255) default null comment 'name',
    `pid`  varchar(10)  default null comment 'parent id',
    primary key (`id`)
)engine = InnoDB ROW_FORMAT = DYNAMIC comment = 'dept';


INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1000', '总公司', NULL);
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1001', '北京分公司', '1000');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1002', '上海分公司', '1000');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1003', '北京研发部', '1001');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1004', '北京财务部', '1001');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1005', '北京市场部', '1001');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1006', '北京研发一部', '1003');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1007', '北京研发二部', '1003');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1008', '北京研发一部一小组', '1006');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1009', '北京研发一部二小组', '1006');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1010', '北京研发二部一小组', '1007');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1011', '北京研发二部二小组', '1007');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1012', '北京市场一部', '1005');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1013', '上海研发部', '1002');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1014', '上海研发一部', '1013');
INSERT INTO `dept`(`id`, `name`, `pid`)
VALUES ('1015', '上海研发二部', '1013');

-- function get_child_list
delimiter
$$
drop function if exists get_child_list$$
create function get_child_list(in_id varchar (10)) returns varchar(1000)
begin
 declare
ids varchar(1000) default '';
 declare
tempids varchar(1000);

 set
tempids = in_id;
 while
tempids is not null do
  set ids = CONCAT_WS(',',ids,tempids);
select GROUP_CONCAT(id)
into tempids
from dept
where FIND_IN_SET(pid, tempids) > 0;
end while;
return ids;
end
$$
delimiter ;

-- #################### localDateTime test ####################
drop table if exists localtime_test;

create table localtime_test
(
    id          bigint      not null primary key auto_increment comment 'id',
    name        varchar(20) not null default 'zero',
    create_time timestamp            default current_timestamp
);

insert into localtime_test (name)
values ('hello'),
       ('world');



