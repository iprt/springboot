package org.iproute.springboot.intergration.server.config;

import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.stereotype.Component;

/**
 * MessageTransformer
 *
 * @author winterfell
 * @since 2022/1/21
 */
@Component
public class MessageTransformer implements GenericTransformer<String, String> {

    @Override
    public String transform(String input) {
        return "Hello " + input + "!";
    }

}