package org.iproute.springboot.design.tree.config;

import org.iproute.springboot.design.tree.model.refresh.RefreshBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.UUID;

/**
 * RefreshConfig
 *
 * @author tech@intellij.io
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
