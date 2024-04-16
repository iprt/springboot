package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo4
 *
 * @author devops@kubectl.net
 * @since 2023/2/5
 */
public class Demo04_WarmUp {
    public static void main(String[] args) {
        TcpServer tcpServer = TcpServer.create()
                .handle((in, out) -> in.receive().then());

        tcpServer.warmup().block();

        DisposableServer server = tcpServer.bindNow();

        server.onDispose().block();
    }
}
