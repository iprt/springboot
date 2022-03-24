package org.iproute.middleware.seata.at.points.service;


import org.iproute.middleware.seata.at.points.dao.PointsDAO;
import org.iproute.middleware.seata.at.points.entity.Points;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 会员积分服务
 */
@Service
public class PointsService {
    @Resource
    private PointsDAO pointsDAO;

    /**
     * 会员增加积分
     *
     * @param username 用户名
     * @param quantity 增加的积分
     * @return 积分对象
     */
    public Points increase(String username, Integer quantity) {
        Points points = pointsDAO.findByUsername(username);
        points.setQuantity(points.getQuantity() + quantity);
        pointsDAO.update(points);
        return points;
    }
}
