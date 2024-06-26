--liquibase formatted sql

-- changeset zhuzhenjie:202301017001
-- precondition-sql-check expectedResult:0 select count(*) from pg_tables where schemaname = 'springboot' and tablename = 'tree_node'
create table tree_node
(
    id          serial4 primary key,
    name        varchar(255)                   not null default 'default',
    pid         int4                           not null default -1,
    level       int2                           not null default -1,
    sort_key    int                            not null default 0,
    create_time timestamp(0) without time zone not null default now(),
    update_time timestamp(0) without time zone not null default now()
);

-- changeset zhuzhenjie:20230108001
comment on table tree_node is 'postgres tree';
comment on column tree_node.id is 'serial id';
comment on column tree_node.name is 'name';
comment on column tree_node.pid is 'parent id';
comment on column tree_node.sort_key is 'sort key';
comment on column tree_node.create_time is 'create time';
comment on column tree_node.update_time is 'update time';