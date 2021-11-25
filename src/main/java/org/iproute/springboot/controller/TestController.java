package org.iproute.springboot.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import org.iproute.springboot.entities.User;
import org.iproute.springboot.repository.mysql.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TestController
 *
 * @author winterfell
 * @since 2021/11/25
 */
@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/sayHello")
    public String sayHello() {
        return "hello world";
    }

    @GetMapping("/mysqlUser")
    @DS("mysql")
    public List<User> mysqlUser() {
        return userMapper.selectList(null);
    }
}

