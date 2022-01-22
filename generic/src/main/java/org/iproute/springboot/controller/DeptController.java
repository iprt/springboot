package org.iproute.springboot.controller;

import org.iproute.springboot.config.aop.RecordParameters;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.po.Dept;
import org.iproute.springboot.repository.zhuzhenjie.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CacheContoller
 *
 * @author winterfell
 * @since 2021/11/30
 */
@RestController
@RecordParameters
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    @RequestLog("获取 Dept")
    @GetMapping("/dept/{id}")
    public List<Dept> dept(@PathVariable("id") long id) {
        return deptMapper.children(id);
    }
}
