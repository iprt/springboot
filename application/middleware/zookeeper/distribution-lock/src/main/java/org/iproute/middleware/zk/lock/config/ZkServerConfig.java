package org.iproute.middleware.zk.lock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * ZkServerConfig
 *
 * @author zhuzhenjie
 * @since 2023/8/7
 */
@ConfigurationProperties(prefix = "zk-server")
@Component
@Data
public class ZkServerConfig {
    private String connectString;
}
