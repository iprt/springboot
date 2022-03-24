package org.iproute.middleware.seata.at.storage.controller;

import org.iproute.middleware.seata.at.storage.entity.Storage;
import org.iproute.middleware.seata.at.storage.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {
    @Resource
    private StorageService storageService;

    @GetMapping("/decrease")
    public Storage decrease(String goodsCode, Integer quantity) {
        return storageService.decrease(goodsCode, quantity);
    }
}
