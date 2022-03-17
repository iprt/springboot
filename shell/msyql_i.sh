#!/bin/bash

source ~/.bashrc
clear
echo "----------------------------------"
echo "Choose Mysql Version: "
echo "(1) Mysql5.7"
echo "(2) Mysql8"
echo "(3) Exit"
echo "----------------------------------"


echo -n "Input your choice: "
read version

case $version in
    1)
    echo "You will install mysql5.7 ."
    ;;
    2)
    echo "You will install mysql8 ."
    ;;
    3)
    echo "Exit!"
    exit
    ;;
    *)
    echo "Bye!"
    exit
    ;;
esac

echo -n  "Input a folder where you want install in: "
read install_dir

if [ ! $install_dir ]; then
   echo "Your input install dir is empty"
   exit
fi


function prepare(){
    cd $1
    mkdir log conf
    chown -R 999 log
    cd -
}


function is_file(){
    iii=$1
    if [ -f "$iii" ]; then
        echo "$iii is a file !!! Can not install !!!"
        exit
    fi
}


if [ ! -d "$install_dir" ]; then
    is_file $install_dir

    echo "Install mysql in $install_dir"
    mkdir -p $install_dir
    prepare $install_dir
elif [ -d "$install_dir" ]; then

    is_file $intall_dir

    if [ "`ls -A $install_dir`" = "" ]; then
        echo "$install_dir is empty."
        prepare $install_dir
    else
        echo "$install_dir is not empty."
        exit
    fi
fi


cd $install_dir


function init_dbenv() {
    echo -n "Please input root password :"
    read root_password

    if [ ! $root_password ]; then
        echo "Root password is not setting !!!"
        echo "Default password is Root@123#"
        root_password="Root@123#"
    else
        echo "Root password is $root_password"
    fi

    echo "MYSQL_ROOT_PASSWORD=$root_password" > db.env
}

function init_dc() {

cat>docker-compose.yml<<EOF
version: "3"

services:
  $1:
    image: $2
    container_name: mysql$3
    network_mode: "host"
    privileged: true
    volumes:
      - "./data:/var/lib/mysql"
      - "./conf/my.cnf:/etc/my.cnf"
      - "./log:/var/log/mysqld"
    environment:
      - MYSQL_ROOT_HOST=%
      - TZ=Asia/Shanghai
    env_file:
      - db.env
EOF

}

function mysql_57_cnf(){
cat>conf/my.cnf<<EOF
[mysql]
port                                   = 3307
socket                                 = /var/run/mysqld/mysqld.sock

[mysqld]
# Required Settings
user                                   = mysql
port                                   = 3307

bind_address                           = 0.0.0.0
datadir                                = /var/lib/mysql
max_allowed_packet                     = 256M

pid-file                               = /var/run/mysqld/mysqld.pid
socket                                 = /var/run/mysqld/mysqld.sock
tmpdir                                 = /tmp

# only on mysql instance visit data dir
skip_external_locking
skip_name_resolve

# ============================== connection Setting ==============================

sql_mode                               = STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION

max_connections                        = 512
max_connect_errors                     = 1000000

back_log                               = 1024
thread_stack                           = 256K
thread_cache_size                      = 128

# time_out need setting
interactive_timeout                    = 3600
wait_timeout                           = 3600

# password
default_authentication_plugin          = mysql_native_password

# ============================== open binlog ==============================
### mysql-bin.000001 mysql-bin.000002
# server-id                            = 1
# log-bin                              = mysql-bin
# log-bin-index                        = mysql-bin-index

# ============================== charset ==============================
character_set_server                   = utf8mb4
collation_server                       = utf8mb4_general_ci

# ============================== 57config
secure_auth                            = 1


# ============================== column config ==============================
# mysql57 enable timestamp insert null
explicit_defaults_for_timestamp        = ON
lower_case_table_names                 = 1


# ============================== innodb config ==============================
default_storage_engine                 = InnoDB

# READ-UNCOMMITTED、READ-COMMITTED、REPEATABLE-READ和SERIALIZABL
transaction_isolation                  = READ-COMMITTED

innodb_buffer_pool_size                = 1G
# @link innodb_buffer_pool_size 1G 一个
innodb_buffer_pool_instances           = 1

innodb_flush_log_at_trx_commit         = 1
innodb_flush_method                    = O_DIRECT

# @link open_files_limit
innodb_open_files                      = 1024
innodb_file_per_table                  = ON

# v * 2
innodb_write_io_threads                = 4
innodb_read_io_threads                 = 4

# v * 2
innodb_thread_concurrency              = 4

# innodb_purge_threads                  = 1

# 缓存还未提交的事务的缓冲区的大小，事务多可以设置更大
innodb_log_buffer_size                 = 16M

# 如果写的场景很多，可以设置的更大
innodb_log_file_size                   = 512M
# 调整日志文件数量, @link innodb_log_file_size
innodb_log_files_in_group              = 2

# 保存脏页百分比的变量 是一个百分比 默认 75
innodb_max_dirty_pages_pct             = 75

innodb_stats_on_metadata               = OFF

# 获取锁的等待时间
innodb_lock_wait_timeout               = 120

innodb_data_file_path                  = ibdata1:1G:autoextend

# ============================== myisam setting ==============================

key_buffer_size                        = 32M
low_priority_updates                   = 1
concurrent_insert                      = 2


# ============================== Buffer Setting ==============================

# 系统默认大小为：512k
join_buffer_size                       = 16M

# 如下是对于16g内存的设置 1M
read_buffer_size                       = 4M
read_rnd_buffer_size                   = 4M

# 每个session 需要做一个排序分配的一个buffer,sort_buffer_size 不指定任何的存储引擎, 默认 256K
sort_buffer_size                       = 16M

# 所有线程能打开的表的数量
# 联动 open_files_limit= table_open_cache*2 @link open_files_limit
table_open_cache                       = 40000
table_definition_cache                 = 40000
table_open_cache_instances             = 64

open_files_limit                       = 60000

# ============================== search config ==============================
# Minimum length of words to be indexed for search results
ft_min_word_len                        = 1


# ============================== query cache ==============================

# close query cache type
query_cache_size                       = 0
query_cache_type                       = 0
# query_cache_limit                    = 1M

# ============================== tmp table config ==============================
tmp_table_size                         = 128M
max_heap_table_size                    = 128M


# ============================== log config ==============================

log_output                             = FILE
log_timestamps                         = SYSTEM

log_error                              = /var/log/mysqld/mysql_error.log

slow_query_log                         = ON     # Disabled for production
log_queries_not_using_indexes          = 1
long_query_time                        = 3
slow_query_log_file                    = /var/log/mysqld/mysql_slow.log
# 记录从服务器产生的慢查询日志
log-slow-admin-statements

general_log                            = OFF
general_log_file                       = /var/log/mysqld/mysql_general.log

[mysqldump]
# ============================== dump config ==============================
quick
quote_names
max_allowed_packet                     = 256M


EOF
}


function mysql_8_cnf(){
cat>conf/my.cnf<<EOF
[mysql]
port                                   = 3306
socket                                 = /var/run/mysqld/mysqld.sock

[mysqld]
# Required Settings
user                                   = mysql
port                                   = 3306

bind_address                           = 0.0.0.0
datadir                                = /var/lib/mysql
max_allowed_packet                     = 256M

pid_file                               = /var/run/mysqld/mysqld.pid
socket                                 = /var/run/mysqld/mysqld.sock
tmpdir                                 = /tmp

# ingore mysqlx plugin. ignore 33060 port
mysqlx                                 = OFF

# only on mysql instance visit data dir
skip_external_locking
skip_name_resolve
# close binlog
skip-log-bin

# Disabling symbolic-links is recommended to prevent assorted security risks
symbolic-links                         = 0

# ============================== Connection Settings ==============================

sql_mode                               = STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION

max_connections                        = 512
max_connect_errors                     = 1000000

back_log                               = 1024
thread_stack                           = 256K
thread_cache_size                      = 128

# time_out need setting
interactive_timeout                    = 3600
wait_timeout                           = 3600

# password
default_authentication_plugin          = mysql_native_password


# ============================== charset ==============================
character_set_server                   = utf8mb4
collation_server                       = utf8mb4_general_ci


# ============================== column config ==============================
# not case sensitive
lower_case_table_names                 = 1


# ============================== Innodb Settings ==============================
default_storage_engine                 = InnoDB

# READ-UNCOMMITTED、READ-COMMITTED、REPEATABLE-READ和SERIALIZABL
transaction_isolation                  = READ-COMMITTED

innodb_buffer_pool_size                = 1G
# @link innodb_buffer_pool_size 1G 一个
innodb_buffer_pool_instances           = 1

innodb_flush_log_at_trx_commit         = 1
innodb_flush_method                    = O_DIRECT

# @link open_files_limit
innodb_open_files                      = 1024
innodb_file_per_table                  = ON

# v * 2
innodb_read_io_threads                 = 4
innodb_write_io_threads                = 4

# 缓存还未提交的事务的缓冲区的大小，事务多可以设置更大
innodb_log_buffer_size                 = 16M

# 如果写的场景很多，可以设置的更大
innodb_log_file_size                   = 512M
# 调整日志文件数量, @link innodb_log_file_size
innodb_log_files_in_group              = 2

# 保存脏页百分比的变量 是一个百分比 默认 75
innodb_max_dirty_pages_pct             = 75

innodb_stats_on_metadata               = OFF

# 获取锁的等待时间
innodb_lock_wait_timeout               = 120

innodb_data_file_path                  = ibdata1:1G:autoextend

# ============================== MyIsam Settings ==============================

key_buffer_size                        = 32M
low_priority_updates                   = 1
concurrent_insert                      = 2



# ============================== Buffer Setting ==============================
# 系统默认大小为：512k
join_buffer_size                       = 16M

# 如下是对于16g内存的设置 1M
read_buffer_size                       = 4M
read_rnd_buffer_size                   = 4M

# 每个session 需要做一个排序分配的一个buffer,sort_buffer_size 不指定任何的存储引擎, 默认 256K
sort_buffer_size                       = 16M

table_open_cache                       = 40000
table_definition_cache                 = 40000
table_open_cache_instances             = 64

open_files_limit                       = 60000

# ============================== search config ==============================
# Minimum length of words to be indexed for search results
ft_min_word_len                        = 1

# mysql8 never use query cache

# ============================== tmp table config ==============================
##
tmp_table_size                         = 128M
max_heap_table_size                    = 128M


# ============================== log config ==============================

log_output                            = FILE
log_timestamps                        = SYSTEM

log_error                             = /var/log/mysqld/mysql_error.log

slow_query_log                        = ON     # Disabled for production
log_queries_not_using_indexes         = 1
long_query_time                       = 3
slow_query_log_file                   = /var/log/mysqld/mysql_slow.log

general_log                           = OFF
general_log_file                      = /var/log/mysqld/mysql_general.log

[mysqldump]
quick
quote_names
max_allowed_packet                   = 256M

EOF
}


case $version in
    1)
    init_dbenv
    init_dc  "mysql57" "mysql:5.7" "57"
    mysql_57_cnf
    sed -i '/^#/d' conf/my.cnf
    echo "Mysql5.7's default port is 3307."
    ;;
    2)
    init_dbenv
    init_dc  "mysql8" "mysql" "8"
    mysql_8_cnf
    sed -i '/^#/d' conf/my.cnf
    echo "Mysql8's default port is 3306."
    ;;
esac
