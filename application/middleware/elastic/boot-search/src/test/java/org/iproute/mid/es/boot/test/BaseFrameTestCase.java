package org.iproute.mid.es.boot.test;

import org.iproute.mid.es.boot.repository.ProductDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

/**
 * 基础框架
 *
 * @author zhuzhenjie
 * @since 2022/7/18
 */
@SpringBootTest
public class BaseFrameTestCase {

    @Autowired
    protected ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    protected ProductDao productDao;


    @BeforeEach
    public void before() {
        System.out.println("|---------------------------|");
        System.out.println("|  BaseFrameTestCase.before |");
        System.out.println("|---------------------------|");
    }


    @AfterEach
    public void after() {
        System.out.println("|---------------------------|");
        System.out.println("|  BaseFrameTestCase.after  |");
        System.out.println("|---------------------------|");
    }
}
