package org.iproute.mid.camel.netty.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.net.InetSocketAddress;

/**
 * ServerInfo
 *
 * @author devops@kubectl.net
 * @since 2022/12/19
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class ServerInfo {
    private String uuid;
    private int port;
}
