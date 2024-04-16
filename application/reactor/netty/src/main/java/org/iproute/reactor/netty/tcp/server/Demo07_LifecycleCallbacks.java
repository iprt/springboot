package org.iproute.reactor.netty.tcp.server;

import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.util.concurrent.TimeUnit;

/**
 * Demo7
 *
 * @author devops@kubectl.net
 * @since 2023/2/5
 */
public class Demo07_LifecycleCallbacks {

    public static void main(String[] args) {

        DisposableServer server = TcpServer.create()
                .doOnConnection(connection ->
                        connection.addHandlerFirst(
                                new ReadTimeoutHandler(10, TimeUnit.SECONDS)
                        )
                )
                .doOnChannelInit((observer, channel, remoteAddress) ->
                        channel.pipeline()
                                .addFirst(new LoggingHandler("reactor.netty.examples"))
                )
                .bindNow();

        server.onDispose().block();
    }
}
