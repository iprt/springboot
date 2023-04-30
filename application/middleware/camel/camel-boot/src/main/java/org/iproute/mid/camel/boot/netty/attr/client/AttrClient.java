package org.iproute.mid.camel.boot.netty.attr.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * AttrClient
 *
 * @author zhuzhenjie
 * @since 4/26/2023
 */
@Slf4j
public class AttrClient {
    public static void main(String[] args) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast("line", new FixedLengthFrameDecoder(UUID.randomUUID().toString().length()));
                    p.addLast("decoder", new StringDecoder());
                    p.addLast("encoder", new StringEncoder());
                    p.addLast("attrClientHandler1", new AttrClientHandler1());
                    p.addLast("attrClientHandler2", new AttrClientHandler2());
                }
            });

            ChannelFuture f = b.connect("127.0.0.1", 7001).sync();
            System.out.println("Client connected");
            // f.channel().closeFuture().sync();

            Channel channel = f.channel();

            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(() -> {

                if (channel.isActive()) {
                    String uuid = UUID.randomUUID().toString();
                    log.info("write msg {}", uuid);
                    channel.writeAndFlush(uuid);
                } else {
                    log.error("channel is not active");
                }

            }, 0, 1, TimeUnit.SECONDS);

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
