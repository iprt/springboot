package org.iproute.springboot.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.iproute.springboot.config.aop.RecordParameters;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.config.mvc.req.PageReq;
import org.iproute.springboot.entities.po.Dept;
import org.iproute.springboot.repository.commons.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * DeptController
 *
 * @author winterfell
 * @since 2021 /11/30
 */
@RestController
@RecordParameters
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * Dept list.
     *
     * @param id the id
     * @return the list
     */
    @RequestLog("获取 Dept")
    @GetMapping("/dept/{id}")
    public List<Dept> dept(@PathVariable("id") long id) {
        return deptMapper.children(id);
    }

    /**
     * Page page.
     *
     * @param pageReq the page req
     * @return the page
     */
    @RequestLog("Dept分页查询")
    @PostMapping("/dept/page")
    public IPage<Dept> page(@RequestBody PageReq pageReq) {
        if (Objects.isNull(pageReq)) {
            throw new IllegalArgumentException("pageReq must not be null");
        }
        int pageNum = Optional.ofNullable(pageReq.getPageNum()).orElse(1);
        int pageSize = Optional.ofNullable(pageReq.getPageSize()).orElse(10);
        boolean searchCount = Optional.ofNullable(pageReq.getSearchCount()).orElse(false);
        Page<Dept> page = new Page<>(pageNum, pageSize, searchCount);
        return deptMapper.selectPage(page,
                Wrappers.<Dept>lambdaQuery()
                        .orderByDesc(Dept::getId)
        );
    }
}
