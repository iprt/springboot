--changeset zhuzhenjie:20230108001
comment on table tree_node is 'postgres tree';
comment on column tree_node.id is 'serial id';
comment on column tree_node.name is 'name';
comment on column tree_node.pid is 'parent id';
comment on column tree_node.sort_key is 'sort key';
comment on column tree_node.create_time is 'create time';
comment on column tree_node.update_time is 'update time';