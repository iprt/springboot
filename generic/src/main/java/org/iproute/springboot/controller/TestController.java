package org.iproute.springboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.aop.RecordParameters;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.bo.CreateTableSql;
import org.iproute.springboot.entities.dto.LocalTimeReq;
import org.iproute.springboot.entities.dto.PostDTO;
import org.iproute.springboot.entities.po.LocalDateTimeTestBean;
import org.iproute.springboot.entities.po.MysqlUser;
import org.iproute.springboot.repository.mysql.UserMapper;
import org.iproute.springboot.repository.zhuzhenjie.CommonMapper;
import org.iproute.springboot.repository.zhuzhenjie.LocalDateTimeTestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TestController
 *
 * @author winterfell
 * @since 2021 /11/25
 */
@RestController
@RecordParameters
@Slf4j
public class TestController {

    @Autowired
    private LocalDateTimeTestMapper localDateTimeTestMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommonMapper commonMapper;

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
    @RequestLog("say hello with name")
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

    /**
     * Localtime test list.
     *
     * @param req the req
     * @return the list
     */
    @RequestLog("LocalDateTime 测试")
    @PostMapping("/localDateTimeTest")
    public List<LocalDateTimeTestBean> localtimeTest(@RequestBody LocalTimeReq req) {
        log.info("req = {}", req);
        return localDateTimeTestMapper.selectList(null);
    }

    /**
     * mybatis plus 多数据源测试
     *
     * @return the list
     */
    @RequestLog("Mysql用户查询")
    @GetMapping("/mysqlUsers")
    public List<MysqlUser> mysqlUser() {
        return userMapper.selectList(null);
    }

    @GetMapping("/showCreateTable/{tableName}")
    public CreateTableSql showCreateTable(@PathVariable("tableName") String tableName) {
        return commonMapper.selectTableCreateSql(tableName);
    }

}

