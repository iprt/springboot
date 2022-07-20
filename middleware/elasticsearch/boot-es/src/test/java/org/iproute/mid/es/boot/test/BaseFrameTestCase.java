package org.iproute.mid.es.boot.test;

import org.iproute.mid.es.boot.repository.ProductDao;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 基础框架
 *
 * @author zhuzhenjie
 * @since 2022/7/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseFrameTestCase {

    @Autowired
    protected ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    protected ProductDao productDao;


    @Before
    public void before() {
        System.out.println("|---------------------------|");
        System.out.println("|  BaseFrameTestCase.before |");
        System.out.println("|---------------------------|");
    }


    @After
    public void after() {
        System.out.println("|---------------------------|");
        System.out.println("|  BaseFrameTestCase.after  |");
        System.out.println("|---------------------------|");
    }
}
