package org.iproute.middleware.seata.at.business.controller;

import org.iproute.middleware.seata.at.business.client.entity.Order;
import org.iproute.middleware.seata.at.business.service.BusinessService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhuzhenjie
 */
@RestController
public class BusinessController {

    @Resource
    private BusinessService businessService;

    /**
     * Test 1 order.
     *
     * @return the order
     */
    @GetMapping("/test1")
    public Order test1() {
        return businessService.sale("coke", 10, "zhangsan", 3, 30f);
    }

    /**
     * Test 2 order.
     *
     * @return the order
     */
    @GetMapping("/test2")
    public Order test2() {
        return businessService.sale("coke", 10000, "zhangsan", 3000, 30000f);
    }
}
