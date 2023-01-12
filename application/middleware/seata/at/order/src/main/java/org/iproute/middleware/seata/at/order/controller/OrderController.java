package org.iproute.middleware.seata.at.order.controller;

import org.iproute.middleware.seata.at.order.entity.Order;
import org.iproute.middleware.seata.at.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author winterfell
 */
@RestController
public class OrderController {
    @Resource
    private OrderService orderService;

    @GetMapping("/create")
    public Order create(String goodsCode, Integer quantity, String username, Integer points, Float amount) {
        return orderService.createOrder(goodsCode, quantity, username, points, amount);
    }
}
