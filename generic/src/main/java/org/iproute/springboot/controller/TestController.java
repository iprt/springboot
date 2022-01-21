package org.iproute.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.dto.PostDTO;
import org.springframework.web.bind.annotation.*;

/**
 * TestController
 *
 * @author winterfell
 * @since 2021 /11/25
 */
@RestController
@Slf4j
public class TestController {

    /**
     * Say hello string.
     *
     * @return the string
     */
    @RequestLog("say hello")
    @GetMapping("/sayHello")
    public String sayHello() {
        return "hello world";
    }

    /**
     * Say hello string.
     *
     * @param name the name
     * @return the string
     */
    @GetMapping("/sayHello/{name}")
    public String sayHello(@PathVariable("name") String name) {
        return "hello " + name;
    }

    /**
     * Post string.
     *
     * @param dto the dto
     * @return the string
     */
    @RequestLog("post 测试")
    @PostMapping("/post")
    public String post(@RequestBody PostDTO dto) {
        log.info("TestController.post | dto = {}", dto);
        return "hello world";
    }
}

