package org.iproute.middleware.zk.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ZookeeperDistributionApplication
 *
 * @author tech@intellij.io
 * @since 2022/3/17
 */
@SpringBootApplication
public class ZookeeperDistributionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperDistributionApplication.class, args);
    }

}
