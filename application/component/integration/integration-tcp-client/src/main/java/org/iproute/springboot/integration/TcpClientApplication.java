package org.iproute.springboot.integration;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.integration.filewriter.FileWriterGateway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import static org.iproute.springboot.integration.filewriter.FileWriterIntegrationConfig.dslConfig;
import static org.iproute.springboot.integration.filewriter.FileWriterIntegrationConfig.javaConfig;
import static org.iproute.springboot.integration.filewriter.FileWriterIntegrationConfig.xmlConfig;

/**
 * @author devops@kubectl.net
 */
@SpringBootApplication
@Slf4j
public class TcpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(TcpClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner writeData(FileWriterGateway gateway, Environment env) {
        return args -> {
            String[] activeProfiles = env.getActiveProfiles();
            if (activeProfiles.length > 0) {
                String profile = activeProfiles[0];
                gateway.writeToFile("simple.txt", "Hello, Spring Integration! (" + profile + ")");
            } else {
                log.error("No active profile set. Should set active profile to one of {}, {}, or {}.",
                        xmlConfig, javaConfig, dslConfig);

            }
        };
    }
}
