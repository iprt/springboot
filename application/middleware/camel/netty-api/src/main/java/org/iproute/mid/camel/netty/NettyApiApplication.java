package org.iproute.mid.camel.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NettyApiApplication 实现netty的动态启停
 *
 * @author zhuzhenjie
 * @since 2022/12/19
 */
@SpringBootApplication
public class NettyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyApiApplication.class, args);
    }

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(10);
    }
}
