package org.iproute.springboot.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisTemplateTest
 *
 * @author tech@intellij.io
 * @since 2023/7/28
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @BeforeAll
    static void beforeAll() {

    }


    @Test
    public void testGet() {

    }

    @Test
    public void testAdd() {

    }

    @Test
    public void testRemove() {

    }

    @AfterAll
    static void afterAll() {

    }
}
