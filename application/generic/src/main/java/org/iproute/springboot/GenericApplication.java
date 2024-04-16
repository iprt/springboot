package org.iproute.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author devops@kubectl.net
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class GenericApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenericApplication.class, args);
    }
}
