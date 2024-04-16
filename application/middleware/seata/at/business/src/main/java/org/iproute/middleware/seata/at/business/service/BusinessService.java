package org.iproute.middleware.seata.at.business.service;

import io.seata.spring.annotation.GlobalTransactional;
import org.iproute.middleware.seata.at.business.client.OrderServiceClient;
import org.iproute.middleware.seata.at.business.client.PointsServiceClient;
import org.iproute.middleware.seata.at.business.client.StorageServiceClient;
import org.iproute.middleware.seata.at.business.client.entity.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 业务逻辑
 *
 * @author devops@kubectl.net
 */
@Service
public class BusinessService {

    @Resource
    private PointsServiceClient pointsServiceClient;
    @Resource
    private StorageServiceClient storageServiceClient;
    @Resource
    private OrderServiceClient orderServiceClient;


    /**
     * 商品销售
     *
     * @param goodsCode 商品编码
     * @param quantity  销售数量
     * @param username  用户名
     * @param points    增加积分
     * @param amount    订单金额
     * @return 订单对象
     */
    @GlobalTransactional(name = "fsp-sale", timeoutMills = 20000, rollbackFor = Exception.class)
    //@Transactional
    public Order sale(String goodsCode, Integer quantity, String username, Integer points, Float amount) {
        pointsServiceClient.increase(username, points);
        storageServiceClient.decrease(goodsCode, quantity);
        Order order = orderServiceClient.create(goodsCode, quantity, username, points, amount);
//        try {
//            // for show undo_log content
//            Thread.sleep(60000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return order;
    }
}
