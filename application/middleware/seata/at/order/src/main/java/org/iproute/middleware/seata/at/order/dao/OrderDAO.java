package org.iproute.middleware.seata.at.order.dao;

import org.iproute.middleware.seata.at.order.entity.Order;

/**
 * @author devops@kubectl.net
 */
public interface OrderDAO {
    /**
     * Insert.
     *
     * @param order the order
     */
    void insert(Order order);
}
