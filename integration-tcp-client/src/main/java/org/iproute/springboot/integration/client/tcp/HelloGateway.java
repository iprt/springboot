package org.iproute.springboot.integration.client.tcp;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * HelloGateway
 *
 * @author winterfell
 * @since 2022/1/21
 */
@MessagingGateway(name = "hello", defaultRequestChannel = "integrationFlow")
@Component
public interface HelloGateway {

    /**
     * Hello string.
     *
     * @param payload the payload
     * @return the string
     */
    @Gateway
    String hello(String payload);

    /**
     * Hello async completable future.
     *
     * @param payload the payload
     * @return the completable future
     */
    @Gateway
    CompletableFuture<String> helloAsync(String payload);
}
