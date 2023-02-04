package org.iproute.reactor.netty.tcp.server;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo3
 *
 * @author winterfell
 * @since 2023/2/5
 */
public class Demo03_Multi {
    public static void main(String[] args) {
        TcpServer tcpServer = TcpServer.create();
        DisposableServer server1 = tcpServer
                .host("localhost")
                .port(8080)
                .bindNow();

        DisposableServer server2 = tcpServer
                .host("0.0.0.0")
                .port(8081)
                .bindNow();

        Mono.when(server1.onDispose(), server2.onDispose())
                .block();
    }
}
