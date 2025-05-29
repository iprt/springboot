package org.iproute.middleware.mqtt.springboot;

import org.iproute.middleware.mqtt.springboot.config.MqttClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * MqttClientApplication
 *
 * @author tech@intellij.io
 * @since 2025-05-29
 */
@EnableConfigurationProperties({MqttClientProperties.class})
@SpringBootApplication
public class MqttClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttClientApplication.class, args);
    }

    public interface Profile {
        String DEV = "dev";
        String PROD = "prod";
    }
}
