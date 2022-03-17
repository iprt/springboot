#!/bin/bash

echo -n  "Input a folder where you want install in: "
read install_dir

if [ ! $install_dir ]; then
   echo "Install dir is empty"
   exit
fi

function prepare(){
    cd $1
    mkdir etc data log
    chown -R 999 log data
    cd -
}

function is_file(){
    if [ -f $1 ]; then
        echo "$1 is a file !!! Can not install !!!"
        exit
    fi
}

if [ ! -d $install_dir ]; then
    is_file $install_dir

    echo "Install redis in $install_dir"
    mkdir -p $install_dir
    prepare $install_dir
elif [ -d $install_dir ]; then
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

cat>docker-compose.yml<<EOF
version: '3'

services:
  redis:
    image: redis
    container_name: redis
    network_mode: host
    volumes:
      - ./etc/redis.conf:/etc/redis.conf
      - ./data:/data
      - ./log:/var/log/redis
      - /etc/localtime:/etc/localtime
    privileged: true
    command: redis-server /etc/redis.conf
EOF


cat>etc/redis.conf<<EOF
# Redis configuration file example.

# ./redis-server /path/to/redis.conf

################################## INCLUDES ###################################
# Default.

################################## INCLUDES ###################################
# Default.

################################## MODULES #####################################
# Default.

################################## NETWORK #####################################
bind                                        0.0.0.0

port                                        6379

tcp-backlog                                 511

timeout                                     0

tcp-keepalive                               300

################################# GENERAL #####################################
daemonize                                   no

supervised                                  no

pidfile                                     /var/run/redis_6379.pid

# loglevel                                    warning
loglevel                                    notice

logfile                                     "/var/log/redis/redis.log"

# # Set the number of databases.
databases                                   16

always-show-logo                            yes

################################ SNAPSHOTTING  ################################

save                                        900 1
save                                        300 10
save                                        60 10000

stop-writes-on-bgsave-error                 yes

rdbcompression                              yes

rdbchecksum                                 yes

dbfilename                                  dump.rdb

# The Append Only File will also be created inside this directory.
dir                                         /data

################################# REPLICATION #################################
# default
replica-serve-stale-data                    yes
replica-read-only                           yes
repl-diskless-sync                          no
repl-diskless-sync-delay                    5
repl-disable-tcp-nodelay                    no

replica-priority                            100

################################## SECURITY ###################################

requirepass                                 123456

################################### CLIENTS ####################################
# default

############################## MEMORY MANAGEMENT ################################
# default

############################# LAZY FREEING ####################################

lazyfree-lazy-eviction                      no
lazyfree-lazy-expire                        no
lazyfree-lazy-server-del                    no
replica-lazy-flush                          no

############################## APPEND ONLY MODE ###############################

appendonly                                  yes

appendfilename                              "appendonly.aof"

appendfsync                                 everysec

no-appendfsync-on-rewrite                   no

auto-aof-rewrite-percentage                 100
auto-aof-rewrite-min-size                   64mb

aof-use-rdb-preamble                        yes

################################ LUA SCRIPTING  ###############################
# Default.
lua-time-limit                              5000

################################ REDIS CLUSTER  ###############################
# Default.

########################## CLUSTER DOCKER/NAT support  ########################
# Default.

################################## SLOW LOG ###################################
# Default.
slowlog-log-slower-than                     10000

slowlog-max-len                             128

################################ LATENCY MONITOR ##############################
# Default.
latency-monitor-threshold                   0

############################# EVENT NOTIFICATION ##############################
# Default.
notify-keyspace-events                      ""

############################### ADVANCED CONFIG ###############################
# Default.
hash-max-ziplist-entries                    512
hash-max-ziplist-value                      64

list-max-ziplist-size                       -2

list-compress-depth                         0

set-max-intset-entries                      512

zset-max-ziplist-entries                    128

zset-max-ziplist-value                      64

hll-sparse-max-bytes                        3000

stream-node-max-bytes                       4096
stream-node-max-entries                     100

activerehashing                             yes

client-output-buffer-limit normal           0 0 0
client-output-buffer-limit replica          256mb 64mb 60
client-output-buffer-limit pubsub           32mb 8mb 60

hz                                          10

dynamic-hz                                  yes

aof-rewrite-incremental-fsync               yes

rdb-save-incremental-fsync                  yes

########################### ACTIVE DEFRAGMENTATION #######################
# Default.
EOF

