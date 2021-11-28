package org.iproute.springboot.controller;

import org.iproute.springboot.entities.dto.PostDTO;
import org.iproute.springboot.repository.mysql.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author winterfell
 * @since 2021 /11/25
 */
@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    /**
     * Say hello string.
     *
     * @return the string
     */
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
    @PostMapping("/post")
    public String post(PostDTO dto) {
        return "hello world";
    }
}

