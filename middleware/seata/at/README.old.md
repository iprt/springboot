# seata at 模式的开发

## <font color=navy>步骤</font>

**1. 业务数据库添加 `undo_log` 表**

- [0_init/init.sql](0_init/init.sql)

**2. 增加依赖 `spring-cloud-alibaba-seata`**

**3. 添加 `registry.conf`, 设置 `registry.conf`**

**4. 添加 `file.conf`，设置 `vgroup_mapping` 与 `grouplist`**

**5. `application.yml` 配置 `tx-service-group`**

**6. 配置数据源代理 `DataSourceProxy`**

## <font color=navy>配置 `seata-server`</font>

基于 `seata-server-1.0.0.zip` 配置的

### <font color=indigo>1. 配置registry.conf</font>

分为两个部分： `registry` 和 `config`

- `registry` 主要配置注册中心之类的，例如 `eureka`, `nacos` 等
  ```conf
  type = "eureka"
  
  eureka {
    serviceUrl = "http://127.0.0.1:8761/eureka"
    # 应用名称
    application = "default"
    weight = "1"
  }
  ```
- `config` 和事务配置信息相关的，`seata`各种配置怎样体现
  ```conf
  # 表名是文件配置
  type = "file"
  
  file {
    # 指向同目录下的 file.conf
    name = "file.conf"
  }
  ```

demo 示例

```conf
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "eureka"

  nacos {
    serverAddr = "localhost"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://127.0.0.1:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```

### <font color=indigo>2. file.conf</font>

service 模块的说明,服务相关的

- `my_test_tx_group`是自定义的事务分组的名字
- `"default"` 是作为一系列配置项的前缀
- `"127.0.0.1:8091"` 代表从这个IP节点上获取事务分组的信息

store存储的是什么呢，存储的是所有的会话信息

- 每一个微服务都要和 `seata-server` 交互
- 默认使用文件的方式存储

测试的情况下 `file.conf`使用默认值即可，如下：

```conf
service {
  #transaction service group mapping
  vgroup_mapping.my_test_tx_group = "default"
  #only support when registry.type=file, please don't set multiple addresses
  default.grouplist = "127.0.0.1:8091"
  #disable seata
  disableGlobalTransaction = false
}

## transaction log store, only used in seata-server
store {
  ## store mode: file、db
  mode = "file"

  ## file store property
  file {
    ## store location dir
    dir = "sessionStore"
  }

  ## database store property
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3306/seata"
    user = "mysql"
    password = "mysql"
  }
}
```

## <font color=navy>RM资源管理器端的配置，Springboot工程</font>

resources 目录下配置 registry.conf 和 file.conf

### <font color=indigo>配置registry.conf</font>

用于注册和登记的

- registry部分
  ```conf
  type = "eureka"
  
  eureka {
    # 注册中心的地址
    serviceUrl = "http://127.0.0.1:8761/eureka"
    
    # seata-server 在注册中心中微服务的名称
    application = "default"
    weight = "1"
  }
  ```
- config部分
  ```conf
  type = "file"
  file {
    name = "file.conf"
  }

 ```


```conf
registry {
  # file 、nacos 、eureka、redis、zk
  type = "eureka"

  nacos {
    serverAddr = "localhost"
    namespace = "public"
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://127.0.0.1:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6381"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = "public"
    cluster = "default"
  }
  apollo {
    app.id = "fescar-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  file {
    name = "file.conf"
  }
}

```

### <font color=indigo>配置file.conf</font>

file.conf 比较关键的配置项

- `vgroup_mapping.fsp_tx_group = "default"`
  ```conf
  #vgroup->rgroup
  # fsp_tx_group 是自定义的事务分组
  vgroup_mapping.fsp_tx_group = "default"
  #only support single node
  # 会从 "127.0.0.1:8091" 获取事务的分组列表
  default.grouplist = "127.0.0.1:8091"
  
  # 上面的"default" 是下面的前缀，这两项的配置是成对出现的
  ```
- `transport` 客户的配置, `transaction` 事务日志的配置

```conf
transport {
  # tcp udt unix-domain-socket
  type = "TCP"
  #NIO NATIVE
  server = "NIO"
  #enable heartbeat
  heartbeat = true
  #thread factory for netty
  thread-factory {
    boss-thread-prefix = "NettyBoss"
    worker-thread-prefix = "NettyServerNIOWorker"
    server-executor-thread-prefix = "NettyServerBizHandler"
    share-boss-worker = false
    client-selector-thread-prefix = "NettyClientSelector"
    client-selector-thread-size = 1
    client-worker-thread-prefix = "NettyClientWorkerThread"
    # netty boss thread size,will not be used for UDT
    boss-thread-size = 1
    #auto default pin or 8
    worker-thread-size = 8
  }
  shutdown {
    # when destroy server, wait seconds
    wait = 3
  }
  serialization = "seata"
  compressor = "none"
}

service {
  #vgroup->rgroup
  vgroup_mapping.fsp_tx_group = "default"
  #only support single node
  default.grouplist = "127.0.0.1:8091"
  #degrade current not support
  enableDegrade = false
  #disable
  disable = false
  #unit ms,s,m,h,d represents milliseconds, seconds, minutes, hours, days, default permanent
  max.commit.retry.timeout = "-1"
  max.rollback.retry.timeout = "-1"
  disableGlobalTransaction = false
}

client {
  async.commit.buffer.limit = 10000
  lock {
    retry.internal = 10
    retry.times = 30
  }
  report.retry.count = 5
  tm.commit.retry.count = 1
  tm.rollback.retry.count = 1
}

transaction {
  undo.data.validation = true
  undo.log.serialization = "jackson"
  undo.log.save.days = 7
  #schedule delete expired undo_log in milliseconds
  undo.log.delete.period = 86400000
  undo.log.table = "undo_log"
}

support {
  ## spring
  spring {
    # auto proxy the DataSource bean
    datasource.autoproxy = false
  }
}
```

### <font color=indigo>3. application.yml的配置</font>

<font color=red>指向之前的 `file.conf` 中的 `vgroup_mapping` 的配置</font>

```yml
spring:
  cloud:
    alibaba:
      seata:
        tx-service-group: fsp_tx_group
```

### <font color=indigo>4. 配置数据源 和 数据源代理 </font>
[DataSourceConfiguration.java](order/src/main/java/org/iproute/middleware/seata/at/order/DataSourceConfiguration.java)
```java
/**
 * 数据源代理
 *
 * @author itlaoqi.com
 */
@Configuration
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 6.1 创建DataSourceProxy
     *
     * @param druidDataSource the druid data source
     * @return the data source proxy
     */
    @Primary
    @Bean("dataSource")
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }

    /**
     * 6.2 将原有的DataSource对象替换为DataSourceProxy
     *
     * @param dataSourceProxy the data source proxy
     * @return the sql session factory
     * @throws Exception the exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:/mapper/*.xml"));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }

}
```


<font color=blue>每个应用的入口添加</font>
```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
```



### <font color=red>5. 在bussiness阶段进行事务的设置</font>

```java

```





