package org.iproute.springboot.design.mysqltree.config;

import org.iproute.springboot.design.mysqltree.model.RefreshBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.UUID;

/**
 * RefreshConfig
 *
 * @author zhuzhenjie
 * @since 2022/12/3
 */
@Configuration
public class RefreshConfig {

    // @RefreshScope
    @Bean
    public RefreshBean refreshBean() {
        return RefreshBean.builder()
                .uuid(UUID.randomUUID().toString())
                .date(new Date())
                .build();
    }

}
