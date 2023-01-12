# Seata1.4.2 AT 模式配置

## `build.gradle`

```gradle
    // seata 1.4.2 config
    implementation('com.alibaba.cloud:spring-cloud-alibaba-seata:2.1.0.RELEASE') {
//        exclude group: "io.seata", module: "seata-all"
        exclude group: "io.seata", module: "seata-spring-boot-starter"
    }
    implementation 'io.seata:seata-all:1.4.2'
```

关键 `exclude group: "io.seata", module: "seata-spring-boot-starter"`

- 这样配置在classpath下的 `registry.conf` 和 `file.conf` 才有效

## `application.yml`

```yml
spring:
  cloud:
  alibaba:
    seata:
      tx-service-group: my_test_tx_group
```

## `DataSourceConfiguration.java`

```txt
@Bean
@ConfigurationProperties(prefix = "spring.datasource")
public DataSource druidDataSource() {
    DruidDataSource druidDataSource = new DruidDataSource();
    return druidDataSource;
}

@Primary
@Bean("dataSource")
public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
    return new DataSourceProxy(druidDataSource);
}

@Bean
public SqlSessionFactory sqlSessionFactory(DataSourceProxy dataSourceProxy) throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSourceProxy);
    sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
            .getResources("classpath*:/mapper/*.xml"));
    sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
    return sqlSessionFactoryBean.getObject();
}
```

## 启动测试

启动 `eureka`

启动 `seata-server`

启动各个微服务 `bussiness` `order` `points` `storage`


## 知识点


