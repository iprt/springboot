package org.iproute.middleware.seata.at.order.service;


import org.iproute.middleware.seata.at.order.dao.OrderDAO;
import org.iproute.middleware.seata.at.order.entity.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 订单服务
 */
@Service
public class OrderService {

    @Resource
    private OrderDAO orderDAO;

    /**
     * 创建新订单
     *
     * @param goodsCode 商品编码
     * @param quantity  采购数量
     * @param username  用户名
     * @param points    增加积分
     * @param amount    订单金额
     * @return 订单对象
     */
    public Order createOrder(String goodsCode, Integer quantity, String username, Integer points, Float amount) {
        Order order = new Order(username, points, goodsCode, quantity, amount);
        orderDAO.insert(order);
        return order;
    }
}
