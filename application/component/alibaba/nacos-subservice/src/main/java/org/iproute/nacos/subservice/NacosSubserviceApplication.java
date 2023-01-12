package org.iproute.nacos.subservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * NacosSubserviceApplication
 *
 * @author zhuzhenjie
 * @since 2023/1/10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosSubserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosSubserviceApplication.class, args);
    }

}
