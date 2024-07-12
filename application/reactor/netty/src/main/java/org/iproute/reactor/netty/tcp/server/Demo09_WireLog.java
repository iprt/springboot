package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo9_WireLog
 *
 * @author tech@intellij.io
 * @since 2023/2/5
 */
public class Demo09_WireLog {

    public static void main(String[] args) {
        DisposableServer server = TcpServer.create()
                .wiretap(true)
                .bindNow();

        server.onDispose().block();
    }
}
