package org.iproute.mid.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * RedisJdbcApplication
 * <p>
 * 注： 使用jdk9 以上
 *
 * @author zhuzhenjie
 * @since 3/15/2023
 */
@SpringBootApplication
public class RedisJdbcApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(RedisJdbcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jdbcTemplate.execute("set hello world");

        List<String> query = jdbcTemplate.query("get hello", (rs, rowNum) -> rs.getString("value"));

        query.forEach(System.out::println);
    }
}
