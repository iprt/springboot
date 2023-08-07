package org.iproute.middleware.zk.lock.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Properties
 *
 * @author zhuzhenjie
 * @since 2023/8/7
 */
@EnableConfigurationProperties(value = {ZkServerConfig.class})
public class Properties {

}
