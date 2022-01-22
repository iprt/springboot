package org.iproute.springboot.integration.client.tcp;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * TcpClientProps
 *
 * @author winterfell
 * @since 2022/1/21
 */
@Component
@ConfigurationProperties("tcp.client")
@Data
public class TcpClientProps {
    private String host;
    private int port;
}
