package org.iproute.component.satoken;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SaTokenApplication
 *
 * @author devops@kubectl.net
 * @since 2023/6/5
 */
@SpringBootApplication
@Slf4j
public class SaTokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaTokenApplication.class, args);
        log.info("启动成功：Sa-Token配置如下：{}", SaManager.getConfig());
    }

}