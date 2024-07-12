package org.iproute.reactor.netty.tcp.server;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo5
 *
 * @author tech@intellij.io
 * @since 2023/2/5
 */
public class Demo05_WritingData {
    public static void main(String[] args) {
        // Sends hello string to the connected clients
        DisposableServer server = TcpServer.create()
                .handle((in, out) ->
                        out.sendString(Mono.just("hello")
                        )
                )
                .bindNow();

        server.onDispose().block();
    }
}
