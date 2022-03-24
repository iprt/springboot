package org.iproute.middleware.seata.at.business.client;

import org.iproute.middleware.seata.at.business.client.entity.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author winterfell
 */
@FeignClient("order-service")
public interface OrderServiceClient {
    /**
     * Create order.
     *
     * @param goodsCode the goods code
     * @param quantity  the quantity
     * @param username  the username
     * @param points    the points
     * @param amount    the amount
     * @return the order
     */
    @GetMapping("/create")
    Order create(@RequestParam(value = "goodsCode") String goodsCode,
                 @RequestParam(value = "quantity") Integer quantity,
                 @RequestParam(value = "username") String username,
                 @RequestParam(value = "points") Integer points,
                 @RequestParam(value = "amount") Float amount);
}
