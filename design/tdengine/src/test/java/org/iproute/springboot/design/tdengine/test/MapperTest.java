package org.iproute.springboot.design.tdengine.test;

import org.iproute.springboot.design.tdengine.entity.Demo;
import org.iproute.springboot.design.tdengine.mapper.DemoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * MapperTest
 *
 * @author zhuzhenjie
 * @since 2022/5/30
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {

    private static final Logger log = LoggerFactory.getLogger(MapperTest.class);
    @Autowired
    private DemoMapper demoMapper;

    @Test
    public void queryTest() {
        List<Demo> demos = demoMapper.selectList(null);
        log.info("demos= {}", demos);
    }

}
