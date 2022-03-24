package org.iproute.middleware.seata.at.storage.service;

import org.iproute.middleware.seata.at.storage.dao.StorageDAO;
import org.iproute.middleware.seata.at.storage.entity.Storage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 仓库服务
 *
 * @author winterfell
 */
@Service
public class StorageService {
    @Resource
    private StorageDAO storageDAO;

    /**
     * 减少库存
     *
     * @param goodsCode 商品编码
     * @param quantity  减少数量
     * @return 库存对象 storage
     */
    public Storage decrease(String goodsCode, Integer quantity) {
        Storage storage = storageDAO.findByGoodsCode(goodsCode);
        if (storage.getQuantity() >= quantity) {
            storage.setQuantity(storage.getQuantity() - quantity);
        } else {
            throw new RuntimeException(goodsCode + "库存不足,目前剩余库存:" + storage.getQuantity());
        }
        storageDAO.update(storage);
        return storage;
    }
}
