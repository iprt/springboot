-- ----------------------------
-- 批量删除 sharding对于的request_log 表
-- ----------------------------
drop procedure if exists batch_delete_request_log;
create procedure batch_delete_request_log()
begin
    declare yyyy int default 0;
    declare yyyy_end int default 0;
    declare mm int default 0;
    set yyyy = 2022;
    set yyyy_end = 2025;
    while yyyy <= yyyy_end
        do
            set mm = 1;
            while mm <= 12
                do
                    # drop table
                    set @_sql = concat('drop table if exists request_log_', yyyy, '_', mm);
                    PREPARE stmt FROM @_sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;
                    set mm = mm + 1;
                end while;
            set yyyy = yyyy + 1;
        end while;
    # reset session value
    set @_sql = '';
end;

-- ----------------------------
--  批量创建 sharding对于的request_log 表
-- ----------------------------
drop procedure if exists batch_create_request_log;
create procedure batch_create_request_log()
begin
    declare yyyy int default 0;
    declare yyyy_end int default 0;
    declare mm int default 0;

    set yyyy = 2022;
    set yyyy_end = 2025;

    while yyyy <= yyyy_end
        do
            set mm = 1;
            while mm <= 12
                do
                    # drop table
                    set @_sql = concat('drop table if exists request_log_', yyyy, '_', mm);
                    PREPARE stmt FROM @_sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;
                    # create table
                    set @`_sql` = concat('create table request_log_', yyyy, '_', mm,
                                         '(',
                                         '`id`           bigint        not null auto_increment comment ''id'',',
                                         ' `request_time` datetime      not null default now() comment ''create time'',',
                                         '`application`  varchar(30)   not null default '''' comment ''application name'',',
                                         '`uname`        varchar(50)   not null default '''' comment ''user name'',',
                                         '`uid`          bigint        not null default -1 comment ''user name'',',
                                         '`uri`          varchar(100)  not null default '''' comment ''request uri'',',
                                         '`success`      tinyint(1)    not null default 0 comment ''success'',',
                                         '`method`       varchar(10)   not null default ''GET'' comment ''method'',',
                                         '`content_type` varchar(30)   not null default '''' comment ''content_type'',',
                                         '`request_desc` varchar(1024) not null default '''' comment ''request desc'',',
                                         '`query_string` text comment ''query string'',',
                                         '`body`         text comment ''request body'',',
                                         '`ip`           varchar(20)   not null default ''127.0.0.1'' comment ''ip'',',
                                         '`user_agent`   varchar(255)  not null default '''' comment ''user agent'',',
                                         'primary key (`id`)',
                                         ') engine = InnoDB ',
                                         ' ROW_FORMAT = DYNAMIC comment = ''请求记录_', yyyy, '_', mm, ''';',
                                         ''
                        );
                    PREPARE stmt FROM @_sql;
                    EXECUTE stmt;
                    DEALLOCATE PREPARE stmt;
                    set mm = mm + 1;
                end while;
            set yyyy = yyyy + 1;
        end while;
    # reset session value;
    set @_sql = '';
END;
