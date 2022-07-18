package org.iproute.mid.es.boot.test.detail;

import org.iproute.mid.es.boot.config.IndexConst;
import org.iproute.mid.es.boot.test.BaseFrameTestCase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;

/**
 * EsBootTtest
 *
 * @author zhuzhenjie
 * @since 2022/7/18
 */
public class IndexTestCase extends BaseFrameTestCase {

    /**
     * 创建索引并增加映射配置，spring data 会自动完成
     */
    @Test
    public void createIndex() {
        // 创建索引，系统初始化会自动创建索引
        boolean exists = elasticsearchRestTemplate.indexOps(
                IndexCoordinates.of(IndexConst.INDEX_NAME)
        ).exists();

        Assert.assertTrue(exists);
    }

    /**
     * 创建索引，系统初始化会自动创建索引
     */
    @Test
    public void deleteIndex() {
        // 创建索引，系统初始化会自动创建索引
        boolean flg = elasticsearchRestTemplate.indexOps(IndexCoordinates.of(IndexConst.INDEX_NAME))
                .delete();
        Assert.assertTrue(flg);
    }
}
