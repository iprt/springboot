package org.iproute.middleware.seata.at.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * old 1.0.0 config: @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
 *
 * @author winterfell
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = "org.iproute.middleware.seata.at.storage.dao")
public class StorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }

}
