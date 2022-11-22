--liquibase formatted sql

--changeset zhuzhenjie:20221122001
create index idx_lft on tree_node (`lft`);
create index idx_rgt on tree_node (`rgt`);
create index idx_lrt_level on tree_node (`lft`, `rgt`, `level`);