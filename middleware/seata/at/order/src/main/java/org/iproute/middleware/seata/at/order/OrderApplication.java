package org.iproute.middleware.seata.at.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * old : @SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
 *
 * @author winterfell
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.iproute.middleware.seata.at.order.dao")
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
