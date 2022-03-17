package org.iproute.middleware.zk.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ZkLockApplication
 *
 * @author winterfell
 * @since 2022/3/17
 */
@SpringBootApplication
public class ZkLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkLockApplication.class, args);
    }

}
