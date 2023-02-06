package org.iproute.springboot.intergration.tcpserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tcp.server")
@Data
public class TcpServerProps {
    private int port;
}
