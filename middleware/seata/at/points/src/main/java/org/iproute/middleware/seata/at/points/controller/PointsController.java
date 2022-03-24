package org.iproute.middleware.seata.at.points.controller;

import org.iproute.middleware.seata.at.points.entity.Points;
import org.iproute.middleware.seata.at.points.service.PointsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PointsController {
    @Resource
    private PointsService pointsService;

    /**
     * 增加积分 （用户名 ， 积分）
     *
     * @param username the username
     * @param quantity the quantity
     * @return the points
     */
    @GetMapping("/increase")
    public Points increase(String username, Integer quantity) {
        return pointsService.increase(username, quantity);
    }
}
