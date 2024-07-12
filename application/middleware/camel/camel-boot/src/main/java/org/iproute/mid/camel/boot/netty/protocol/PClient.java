package org.iproute.mid.camel.boot.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * ClientTest
 *
 * @author tech@intellij.io
 * @since 2022/8/8
 */
@Slf4j
public class PClient {

    public static void main(String[] args) {

        NioEventLoopGroup g = new NioEventLoopGroup(1);

        Bootstrap bootstrap = new Bootstrap();

        bootstrap.group(g)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new MyProtocolDecoder());
                        ch.pipeline().addLast(new MyProtocolEncoder());
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<MyMsg>() {

                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                // 发送10条数据
                                // ctx.channel().eventLoop().execute(() -> {
                                //     for (int i = 0; i < 10; i++) {
                                //         try {
                                //             Thread.sleep(1000);
                                //         } catch (InterruptedException e) {
                                //             throw new RuntimeException(e);
                                //         }
                                //
                                //         String msg = "hello world " + i;
                                //
                                //         byte[] bytes = msg.getBytes();
                                //         ctx.writeAndFlush(new MyProtocol(
                                //                 bytes.length,
                                //                 bytes
                                //         ));
                                //
                                //     }
                                // });

                                // https://github.com/netty/netty/issues/9908

                                log.info("client channel active");
                            }

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, MyMsg msg) throws Exception {
                                // byte[] content = msg.getContent();
                                log.info("接收到服务端的消息：{}", msg.getData());
                            }
                        });
                    }
                });

        try {
            Channel channel = bootstrap.connect("127.0.0.1", 7001).sync().channel();

            int i = 0;
            for (; ; ) {
                if (!channel.isActive()) {
                    break;
                }

                TimeUnit.SECONDS.sleep(1);

                String data = "hello world " + i++;

                channel.writeAndFlush(MyMsg.builder()
                        .data(data)
                        .build());
                if (i == 10001) {
                    break;
                }
            }

            // channel.closeFuture().sync();
        } catch (Exception e) {
            log.error("", e);
        } finally {
            g.shutdownGracefully();
        }

    }
}
