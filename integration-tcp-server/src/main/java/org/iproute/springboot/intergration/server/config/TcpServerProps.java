package org.iproute.springboot.intergration.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TcpServerProps
 *
 * @author winterfell
 * @since 2022/1/21
 */
@Component
@ConfigurationProperties("tcp.server")
@Data
public class TcpServerProps {
    int port;
}
