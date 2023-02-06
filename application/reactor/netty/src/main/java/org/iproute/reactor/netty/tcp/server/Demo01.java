package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo1
 *
 * @author zhuzhenjie
 * @since 2023/2/5
 */
public class Demo01 {
    public static void main(String[] args) {
        // DisposableServer 一次性的
        DisposableServer server =
                TcpServer.create() // 1. Creates a TcpServer instance that is ready for configuring.
                        .bindNow();// 2. Starts the server in a blocking fashion and waits for it to finish initializing.

        // ???
        server.onDispose()
                .block();

    }
}
