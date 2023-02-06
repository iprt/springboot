package org.iproute.springboot.alibaba.sentinel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 *
 * @author zhuzhenjie
 * @since 2022/4/6
 */
@RestController
public class HelloController {

    /**
     * Hello string.
     *
     * @return the string
     */
    @GetMapping("/hello")
    public String hello() {
        return "Hello,Sentinel!";
    }
}
