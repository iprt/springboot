package org.iproute.reactor.netty.tcp.server;

import io.netty.channel.ChannelOption;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

/**
 * Demo8_ChannelOptions
 *
 * @author tech@intellij.io
 * @since 2023/2/5
 */
public class Demo08_ChannelOptions {
    public static void main(String[] args) {
        DisposableServer server = TcpServer.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .bindNow();

        server.onDispose().block();
    }
}
