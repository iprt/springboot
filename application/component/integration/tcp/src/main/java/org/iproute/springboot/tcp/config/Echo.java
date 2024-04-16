package org.iproute.springboot.tcp.config;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;

/**
 * Echo
 *
 * @author devops@kubectl.net
 * @since 2022/1/23
 */
@MessageEndpoint
public class Echo {

    @Transformer(inputChannel = "fromTcp", outputChannel = "toEcho")
    public String convert(byte[] bytes) {
        return new String(bytes);
    }

    @ServiceActivator(inputChannel = "toEcho")
    public String upCase(String in) {
        return in.toUpperCase();
    }

    @Transformer(inputChannel = "resultToString")
    public String convertResult(byte[] bytes) {
        return new String(bytes);
    }
}
