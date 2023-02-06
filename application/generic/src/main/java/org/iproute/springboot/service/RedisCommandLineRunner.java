package org.iproute.springboot.service;

import org.iproute.springboot.config.redis.RedisUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * RestTest
 *
 * @author zhuzhenjie
 * @since 2022/1/23
 */
@Component
public class RedisCommandLineRunner implements CommandLineRunner {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCommandLineRunner(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        String uuid = UUID.randomUUID().toString();
        RedisUtil.StringOps.set(uuid, uuid);
        RedisUtil.StringOps.get(uuid);

        stringRedisTemplate.delete(uuid);
    }
}
