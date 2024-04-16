package org.iproute.middleware.seata.at;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author devops@kubectl.net
 */
@SpringBootApplication
@EnableEurekaServer
public class SeataAtEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeataAtEurekaApplication.class, args);
    }

}