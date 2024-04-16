package org.iproute.mid.camel.boot.netty.serverloopsend;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * ReceiveClient
 *
 * @author devops@kubectl.net
 * @since 2022/8/20
 */
@Slf4j
public class LoopClient {

    public static void main(String[] args) {

        NioEventLoopGroup g = new NioEventLoopGroup(2);

        Bootstrap boot = new Bootstrap();

        boot.group(g)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                        ch.pipeline().addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new SimpleChannelInboundHandler<String>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                                        log.info("receive server message | {}", msg);

                                        ctx.writeAndFlush("client receive success.");
                                    }
                                });
                    }
                });

        try {
            Channel channel = boot.connect("127.0.0.1", 7003).sync().channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            g.shutdownGracefully();
        }

    }
}
