--liquibase formatted sql

-- changeset devops:20240219_01
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

-- changeset devops:20240422_01
drop table if exists student;
create table student
(
    id           bigserial   not null primary key,
    student_name varchar(50) not null default 'name',
    create_time  timestamp   not null default current_timestamp
);
comment on table student is 'student';
comment on column student.id is 'id';
comment on column student.student_name is 'Student Name';

insert into student(student_name)
values
    ('stu_001'),
    ('stu_002');


-- changeset devops:20240422_02
drop table if exists teacher;
create table teacher
(
    id           bigserial   not null primary key,
    teacher_name varchar(50) not null default 'name',
    create_time  timestamp   not null default current_timestamp
);
comment on table teacher is 'teacher';
comment on column teacher.id is 'id';
comment on column teacher.teacher_name is 'Teacher Name';

insert into teacher(teacher_name)
values
    ('teacher_001'),
    ('teacher_002'),
    ('teacher_003');

-- changeset devops:20240513_01
drop table if exists demo_bean;
create table demo_bean
(
    id          bigserial    not null primary key,
    name        varchar(100) not null default 'unknown',
    create_time timestamp    not null default current_timestamp,
    update_time timestamp    not null default current_timestamp
);
comment on table demo_bean is 'demo';
comment on column demo_bean.id is 'id';
comment on column demo_bean.name is 'name';


-- changeset devops:20240605_01
drop table if exists demo_bean_leaf;
create table demo_bean_leaf
(
    id          bigint       not null primary key,
    name        varchar(100) not null default 'unknown',
    create_time timestamp    not null default current_timestamp,
    update_time timestamp    not null default current_timestamp
);
comment on table demo_bean_leaf is 'demo';
comment on column demo_bean_leaf.id is 'id 基于snowflake算法生产的id';
comment on column demo_bean_leaf.name is 'name';

-- changeset devops:20250116_01
drop table if exists demo_user;
create table demo_user
(
    id    bigint       not null primary key,
    name  varchar(100) not null default 'unknown',
    age   int          not null default 0,
    email varchar(100) not null default 'abc@examples.com'
);
comment on table demo_user is 'demo_user';
comment on column demo_user.id is 'id';
comment on column demo_user.name is 'name';
comment on column demo_user.age is 'age';
comment on column demo_user.email is 'email';

-- changeset devops:20250116_02
insert into demo_user(id, name, age, email)
values
    (1, 'admin', 18, 'devops@kubctl.net');
