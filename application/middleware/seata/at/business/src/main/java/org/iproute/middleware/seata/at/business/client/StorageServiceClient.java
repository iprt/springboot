package org.iproute.middleware.seata.at.business.client;

import org.iproute.middleware.seata.at.business.client.entity.Storage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author zhuzhenjie
 */
@FeignClient("storage-service")
public interface StorageServiceClient {
    /**
     * Decrease storage.
     *
     * @param goodsCode the goods code
     * @param quantity  the quantity
     * @return the storage
     */
    @GetMapping("/decrease")
    Storage decrease(@RequestParam(value = "goodsCode") String goodsCode,
                     @RequestParam(value = "quantity") Integer quantity);
}
