package org.iproute.springboot.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * RedisController
 *
 * @author zhuzhenjie
 * @since 2022/7/20
 */
@AllArgsConstructor
@RestController
@Slf4j
public class RedisController {

    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("/redis/lua")
    public String lua() {
        List<String> keys = Arrays.asList("testLua", "hello redis lua script");
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(
                "local key = KEYS[1]; local val = KEYS[2]; local expire = ARGV[1]; redis.call(\"set\", key, val); redis.call(\"expire\", key, expire);",
                Boolean.class
        );

        Boolean execute = stringRedisTemplate.execute(redisScript, keys, "100");

        if ((execute == null)) {
            return "failure";
        }

        log.info("lua script execute result : {}", execute);
        return "success";
    }

}
