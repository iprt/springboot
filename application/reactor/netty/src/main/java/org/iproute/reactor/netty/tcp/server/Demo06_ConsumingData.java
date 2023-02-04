package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo6ConsumingData
 *
 * @author winterfell
 * @since 2023/2/5
 */
public class Demo06_ConsumingData {
    public static void main(String[] args) {
        DisposableServer server = TcpServer.create()
                .handle((in, out) ->
                        in.receive().then()
                )
                .bindNow();

        server.onDispose().block();
    }
}
