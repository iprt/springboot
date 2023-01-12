package org.iproute.middleware.seata.at;

import org.iproute.middleware.seata.at.order.entity.Order;
import org.iproute.middleware.seata.at.order.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderApplicationTests {
    @Resource
    private OrderService orderService;

    @Test
    public void testOrder(){
        Order order = orderService.createOrder("coke", 3, "zhangsan", 3, 30f);
        System.out.println(order);
    }

}
