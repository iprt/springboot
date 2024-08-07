package org.iproute.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.aop.RecordParameters;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.bo.CreateTableSql;
import org.iproute.springboot.entities.dto.PostDTO;
import org.iproute.springboot.entities.po.mysql.MysqlUser;
import org.iproute.springboot.repository.commons.CommonMapper;
import org.iproute.springboot.repository.mysql.MysqlUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CommonController
 *
 * @author tech@intellij.io
 * @since 2021 /11/25
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RecordParameters
@Slf4j
public class CommonController {
    private final CommonMapper commonMapper;
    private final MysqlUserMapper mysqlUserMapper;

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

    @GetMapping("/mysqlUsers")
    public List<MysqlUser> mysqlUsers() {
        return mysqlUserMapper.selectList(null);
    }

    @GetMapping("/showCreateTable/{tableName}")
    public CreateTableSql showCreateTable(@PathVariable("tableName") String tableName) {
        return commonMapper.selectTableCreateSql(tableName);
    }

}

