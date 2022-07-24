package org.iproute.mid.logstash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * LogstashApplication
 *
 * @author zhuzhenjie
 * @since 2022/7/24
 */
@SpringBootApplication
public class LogstashApplication {

    public static void main(String[] args) {
       SpringApplication.run(LogstashApplication.class, args);
    }
}
