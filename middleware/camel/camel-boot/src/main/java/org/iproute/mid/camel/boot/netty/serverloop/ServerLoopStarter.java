package org.iproute.mid.camel.boot.netty.serverloop;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * LoopSendSever
 *
 * @author zhuzhenjie
 * @since 2022/8/20
 */
@Slf4j
public class ServerLoopStarter {

    public static void main(String[] args) {

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(4);

        ServerBootstrap server = new ServerBootstrap();


        server.group(boss, worker).channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new ServerLoopHandler());
                    }
                });


        try {
            Channel channel = server.bind(7003).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
