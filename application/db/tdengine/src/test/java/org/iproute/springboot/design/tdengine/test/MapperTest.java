package org.iproute.springboot.design.tdengine.test;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.design.tdengine.entity.Demo;
import org.iproute.springboot.design.tdengine.mapper.DemoMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;

/**
 * MapperTest
 *
 * @author zhuzhenjie
 * @since 2022/5/30
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class MapperTest {

    @Autowired
    private DemoMapper demoMapper;

    @Test
    @Order(1)
    public void testDropTable() {
        demoMapper.dropTable();
    }

    @Test
    @Order(2)
    public void testCreateTable() {
        demoMapper.createTable();
    }

    @Test
    @Order(3)
    public void testInsert() {
        IntStream.range(1, 11).forEach(i -> {
            demoMapper.insert("test-insert-" + i);
        });

    }

    @Test
    @Order(4)
    public void testQuery() {
        List<Demo> demos = demoMapper.selectList(null);
        // log.info("demos= {}", demos);

        demos.forEach(demo -> {
            log.info("demo info : {}", demo);
        });
    }

}
