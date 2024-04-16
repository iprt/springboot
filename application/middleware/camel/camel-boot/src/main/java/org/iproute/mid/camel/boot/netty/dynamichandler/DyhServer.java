package org.iproute.mid.camel.boot.netty.dynamichandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.dynamichandler.serverhandler.ServerInitializer;

/**
 * DyhServer
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class DyhServer {

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(4);

        ServerBootstrap server = new ServerBootstrap();

        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ServerInitializer());

        try {
            int port = 7002;
            log.info("Server start port at {}", port);
            Channel channel = server.bind(port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("server error", e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

}
