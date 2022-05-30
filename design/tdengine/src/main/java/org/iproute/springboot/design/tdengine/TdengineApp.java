package org.iproute.springboot.design.tdengine;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.design.tdengine.mapper.DemoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TdengineApp
 *
 * @author zhuzhenjie
 * @since 2022/5/30
 */
@SpringBootApplication
@Slf4j
public class TdengineApp implements CommandLineRunner {

    @Autowired
    private DemoMapper demoMapper;

    public static void main(String[] args) {
        SpringApplication.run(TdengineApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("demo --> {}", demoMapper.selectList(null));
    }
}
