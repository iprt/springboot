package org.iproute.middleware.seata.at.business.client;

import org.iproute.middleware.seata.at.business.client.entity.Points;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author devops@kubectl.net
 */
@FeignClient("points-service")
public interface PointsServiceClient {
    /**
     * Increase points.
     *
     * @param username the username
     * @param quantity the quantity
     * @return the points
     */
    @GetMapping("/increase")
    Points increase(@RequestParam(value = "username") String username,
                    @RequestParam(value = "quantity") Integer quantity);
}
