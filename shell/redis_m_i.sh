#!/bin/bash
echo "This shell will install redis master and redis sentinel"
echo -n  "Input a folder where you want install redis: "
read redis_install_dir

echo -n  "Input a folder where you want install sentinel: "
read sentinel_install_dir

if [ ! $redis_install_dir ]; then
   echo "Your input install dir is empty"
   exit
fi

if [ ! $sentinel_install_dir ]; then
   echo "Your input install dir is empty"
   exit
fi


function is_file(){
    install_dir=$1
    if [ -f "$install_dir" ]; then
        echo "$install_dir is a file !!! Can not install !!!"
        exit
    fi
}

# setting requirepass
echo -n  "Input require pass: "
read requirepass

if [ ! $requirepass ]; then
    echo "requirepass is not setting !!!"
    echo "Default requirepass is 123456"
    requirepass="123456"
else
    echo "requirepass is $requirepass"
fi

# setting masterauth
echo -n  "Input master auth: "
read masterauth
if [ ! $masterauth ]; then
    echo "masterauth is not setting !!!"
    echo "Default masterauth is 123456"
    masterauth="123456"
else
    echo "masterauth is $masterauth"
fi

echo "default redis port is 6379 !!!"
echo "default sentinel port is 26379 !!!"

# install master
function prepare_redis(){
    cd $1
    mkdir etc data log
    chown -R 999 log data
    cd -
}

if [ ! -d "$redis_install_dir" ]; then
    is_file $redis_install_dir

    echo "Install redis mater in $redis_install_dir"
    mkdir -p $redis_install_dir
    prepare_redis $redis_install_dir
elif [ -d "$redis_install_dir" ]; then
    is_file $redis_install_dir

    if [ "`ls -A $redis_install_dir`" = "" ]; then
        echo "$redis_install_dir is empty."
        prepare_redis $redis_install_dir
    else
        echo "$redis_install_dir is not empty."
        exit
    fi
fi

cd $redis_install_dir

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

loglevel                                    warning

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
# redis replication config
# replicaof 80.111.14.3 10011
masterauth $masterauth

replica-serve-stale-data                    yes
replica-read-only                           yes
repl-diskless-sync                          no
repl-diskless-sync-delay                    5
repl-disable-tcp-nodelay                    no

replica-priority                            100

################################## SECURITY ###################################

requirepass                                 $requirepass

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

cd -

# install sentinel
function prepare_sentinel(){
    cd $1
    mkdir conf log
    chown -R 999 log
    cd -
}

if [ ! -d "$sentinel_install_dir" ]; then
    is_file $sentinel_install_dir

    echo "Install redis sentinel in $sentinel_install_dir"
    mkdir -p $sentinel_install_dir
    prepare_sentinel $sentinel_install_dir
elif [ -d "$sentinel_install_dir" ]; then
    is_file $sentinel_install_dir

    if [ "`ls -A $sentinel_install_dir`" = "" ]; then
        echo "$sentinel_install_dir is empty."
        prepare_sentinel $sentinel_install_dir
    else
        echo "$sentinel_install_dir is not empty."
        exit
    fi
fi

cd $sentinel_install_dir

echo -n  "Input redis master ip address: "
read masterip

if [ ! $masterip ]; then
   echo "Your input master ip is empty. Default master ip is 127.0.0.1"
   masterip="127.0.0.1"
else
  echo "Your input master ip is $masterip"
fi

cat>docker-compose.yml<<EOF
version: '3'

services:
  redis-sentinel:
    image: redis
    container_name: redis-sentinel
    network_mode: host
    volumes:
      - ./conf:/etc/redis-sentinel
      - ./log:/var/log/redis-sentinel
      - /etc/localtime:/etc/localtime
    privileged: true
    command: redis-sentinel /etc/redis-sentinel/sentinel.conf
EOF

cat>conf/sentinel.conf<<EOF
port 26379

daemonize no

pidfile "/var/run/redis-sentinel.pid"

logfile "/var/log/redis-sentinel/sentinel.log"

dir "/tmp"

sentinel monitor redis-master $masterip 6379 2

sentinel auth-pass redis-master $masterauth

sentinel down-after-milliseconds redis-master 30000

# requirepass <password>
# sentinel sentinel-user <username>
# sentinel sentinel-pass <password>

sentinel parallel-syncs redis-master 1

sentinel failover-timeout redis-master 180000

# sentinel notification-script mymaster /var/redis/notify.sh

# sentinel client-reconfig-script mymaster /var/redis/reconfig.sh

EOF
