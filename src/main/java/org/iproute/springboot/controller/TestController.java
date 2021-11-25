package org.iproute.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author winterfell
 * @since 2021/11/25
 */
@RestController
public class TestController {

    @GetMapping("/sayHello")
    public String sayHello() {
        return "hello world";
    }
}

