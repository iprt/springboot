#!/bin/bash
yum update -y

yum install -y yum-utils device-mapper-persistent-data  lvm2 curl

yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

yum makecache fast

yum install -y docker-ce docker-ce-cli containerd.io

systemctl start docker

cat>/etc/docker/daemon.json<<EOF
{
  "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn"]
}
EOF

systemctl daemon-reload

systemctl restart docker

systemctl enable docker

echo 'Install docker-compose'

curl -L https://get.daocloud.io/docker/compose/releases/download/1.29.2/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose

chmod +x /usr/local/bin/docker-compose
