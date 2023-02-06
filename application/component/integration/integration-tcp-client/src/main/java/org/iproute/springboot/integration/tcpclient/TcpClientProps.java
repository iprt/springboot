package org.iproute.springboot.integration.tcpclient;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("tcp.client")
@Data
public class TcpClientProps {
    private String host;
    private int port;
}
