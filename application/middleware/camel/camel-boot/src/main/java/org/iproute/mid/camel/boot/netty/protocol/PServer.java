package org.iproute.mid.camel.boot.netty.protocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * ServerTest
 *
 * @author tech@intellij.io
 * @since 2022/8/8
 */
@Slf4j
public class PServer {

    public static void main(String[] args) {

        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(2);

        ServerBootstrap server = new ServerBootstrap();

        server.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyProtocolDecoder());
                        ch.pipeline().addLast(new MyProtocolEncoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<MyMsg>() {

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                String data = "Netty,Rock!";
                                ctx.writeAndFlush(
                                        MyMsg.builder().data(data).build()
                                );
                                ctx.fireChannelActive();
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, MyMsg msg) throws Exception {
                                String receiveData = msg.getData();
                                log.info("接收到客户端消息|{}", receiveData);
                                // echo
                                ctx.writeAndFlush(msg);
                            }
                        });

                    }
                });

        try {
            final int port = 7001;
            Channel channel = server.bind(port).sync().channel();
            log.info("Protocol server listening on {}", port);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
