package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo2
 *
 * @author devops@kubectl.net
 * @since 2023/2/5
 */
public class Demo02_HostPort {
    public static void main(String[] args) {
        DisposableServer server = TcpServer.create()
                .host("localhost")
                .port(8080)
                .bindNow();

        server.onDispose()
                .block();
    }
}
