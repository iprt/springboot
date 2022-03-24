package org.iproute.middleware.seata.at.points;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 6.3 排除默认数据源配置类
 *
 * @author winterfell
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
@MapperScan(basePackages = "org.iproute.middleware.seata.at.points.dao")
public class PointsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PointsApplication.class, args);
    }

}
