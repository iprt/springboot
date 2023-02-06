package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.resources.LoopResources;
import reactor.netty.tcp.TcpServer;

/**
 * Demo13_EventLoop
 *
 * @author zhuzhenjie
 * @since 2023/2/5
 */
public class Demo13_EventLoop {
    public static void main(String[] args) {
        LoopResources loop = LoopResources.create("event-loop", 1, 4, true);
        DisposableServer server =
                TcpServer.create()
                        .runOn(loop)
                        .bindNow();

        server.onDispose()
                .block();
    }
}
