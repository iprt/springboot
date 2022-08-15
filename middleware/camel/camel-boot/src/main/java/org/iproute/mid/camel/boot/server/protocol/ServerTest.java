package org.iproute.mid.camel.boot.server.protocol;

import cn.hutool.core.util.StrUtil;
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
 * @author zhuzhenjie
 * @since 2022/8/8
 */
@Slf4j
public class ServerTest {

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
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<MyProtocol>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, MyProtocol msg) throws Exception {
                                byte[] content = msg.getContent();
                                String s = new String(content);
                                log.info("接收到客户端消息|{}", s);
                                String rtMsg = s.toUpperCase();

                                byte[] rtContent = rtMsg.getBytes();

                                ctx.writeAndFlush(MyProtocol.builder()
                                        .len(rtContent.length)
                                        .content(rtContent)
                                        .build());
                            }
                        });

                    }
                });

        try {
            Channel channel = server.bind(7001).sync().channel();

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
