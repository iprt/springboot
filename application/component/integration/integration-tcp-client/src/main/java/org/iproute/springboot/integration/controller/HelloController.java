package org.iproute.springboot.integration.controller;

import org.iproute.springboot.integration.tcpclient.HelloGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloController {

    @Resource
    private HelloGateway helloGateway;

    @GetMapping(path = "/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return this.helloGateway.hello(name);
    }
}
