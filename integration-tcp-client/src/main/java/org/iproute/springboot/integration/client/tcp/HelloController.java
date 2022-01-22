package org.iproute.springboot.integration.client.tcp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author winterfell
 * @since 2022/1/21
 */
@RestController
public class HelloController {

    private final HelloGateway helloGateway;

    public HelloController(HelloGateway helloGateway) {
        this.helloGateway = helloGateway;
    }

    @GetMapping(path = "{name}")
    public String hello(@PathVariable("name") String name) {
        String hello = helloGateway.hello(name);
        return hello;
    }

}
