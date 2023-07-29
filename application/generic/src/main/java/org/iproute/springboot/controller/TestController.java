package org.iproute.springboot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.aop.RecordParameters;
import org.iproute.springboot.config.atx.ApplicationContextUtils;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.config.sharding.ShardingAlgorithmTool;
import org.iproute.springboot.entities.bo.CreateTableSql;
import org.iproute.springboot.entities.dto.PostDTO;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.iproute.springboot.entities.po.mysql.MysqlUser;
import org.iproute.springboot.repository.commons.CommonMapper;
import org.iproute.springboot.repository.mysql.MysqlUserMapper;
import org.iproute.springboot.repository.springboot.RequestLogBeanMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * TestController
 *
 * @author zhuzhenjie
 * @since 2021 /11/25
 */
@AllArgsConstructor
@RestController
@RecordParameters
@Slf4j
public class TestController {
    private final CommonMapper commonMapper;
    private final MysqlUserMapper mysqlUserMapper;
    private final RequestLogBeanMapper requestLogBeanMapper;

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

    @GetMapping("/reqLog/all")
    public List<RequestLogBean> allRequestLogBean() {
        return requestLogBeanMapper.selectList(null);
    }


    @GetMapping("/atxUtils")
    public String atxUtils() {
        return ApplicationContextUtils.getBean(CommonMapper.class).toString();
    }

    @GetMapping("/tables")
    public Set<String> tables() {
        log.info("ShardingAlgorithmTool 的类加载器为 : {}", ShardingAlgorithmTool.class.getClassLoader());
        return ShardingAlgorithmTool.TABLE_NAME_CACHE;
    }

}

