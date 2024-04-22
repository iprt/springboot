--liquibase formatted sql

-- changeset zhuzhenjie:20240219_01
drop table if exists book;
create table book
(
    id      bigserial not null primary key,
    detail  json      not null,
    authors json      not null,
    types   varchar[] not null
);
comment on table book is 'book';
comment on column book.id is 'id';
comment on column book.detail is 'detail';
comment on column book.authors is 'authors';
comment on column book.types is 'types';

-- changeset zhuzhenjie:20240219_01