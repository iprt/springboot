package org.iproute.middleware.zk.lock.controller;

import lombok.extern.slf4j.Slf4j;
import org.iproute.middleware.zk.lock.service.WarehouseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OrderController
 *
 * @author winterfell
 * @since 2022/3/17
 */
@RestController
@Slf4j
public class OrderController {

    @Resource
    private WarehouseService warehouseService;

    /**
     * Create order string.
     *
     * @param name the name
     * @return the string
     */
    @GetMapping("/create_order")
    public String createOrder(String name) {
        try {
            // 模拟业务
            /*
            int i = warehouseService.outOfWarehouse();
             */

            int i = warehouseService.outOfWarehouseWithLock();

            log.info("ThreadName is {}, 商品出库成功，库存剩余{}", Thread.currentThread().getName(), i);
            return "success";
        } catch (Exception e) {
            log.error("ThreadName is {}, 商品出库失败, {}", Thread.currentThread().getName(), e.getMessage());
            return "failure";
        }
    }


    /**
     * Reset string.
     *
     * @return the string
     */
    @GetMapping("/reset")
    public String reset() {
        WarehouseService.shoe = 10;
        return "reset";
    }

}
