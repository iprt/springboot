package org.iproute.springboot.intergration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author devops@kubectl.net
 */
@SpringBootApplication
public class TcpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcpServerApplication.class, args);
    }

}
