package org.iproute.middleware.seata.at.storage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.iproute.middleware.seata.at.storage.entity.Storage;
import org.springframework.stereotype.Repository;

/**
 * The interface Storage dao.
 *
 * @author winterfell
 */
@Repository
@Mapper
public interface StorageDAO {
    /**
     * Find by goods code storage.
     *
     * @param goodsCode the goods code
     * @return the storage
     */
    Storage findByGoodsCode(String goodsCode);

    /**
     * Update.
     *
     * @param storage the storage
     */
    void update(Storage storage);
}
