package org.iproute.nacos.subservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * NacosClientApplication
 *
 * @author zhuzhenjie
 * @since 2023/1/10
 */
@SpringBootApplication
@EnableDiscoveryClient
public class NacosClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientApplication.class, args);
    }

}
