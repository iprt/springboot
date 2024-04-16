package org.iproute.mid.camel.boot.netty.echoString;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * EchoStringClient
 *
 * @author devops@kubectl.net
 * @since 5/1/2023
 */
public class EchoStringClient {
    public static void main(String[] args) {
        final int PORT = 8002;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline p = ch.pipeline();
                    p.addLast("fixedLength", new FixedLengthFrameDecoder(UUID.randomUUID().toString().length()));
                    p.addLast("stringDecoder", new StringDecoder());
                    p.addLast("stringEncoder", new StringEncoder());
                    p.addLast("biz", new SimpleChannelInboundHandler<String>() {

                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            System.out.println("channelActive");
                        }

                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
                            System.out.println("receive from server; msg = " + msg);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            System.err.println("lost server's connection");
                            ctx.close();
                        }
                    });
                }
            });

            final String HOST = "127.0.0.1";
            ChannelFuture f = b.connect(HOST, PORT).sync();
            System.out.println("netty client connect to " + HOST + ":" + PORT);

            Channel channel = f.channel();

            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(() -> {
                if (channel.isActive()) {
                    String uuid = UUID.randomUUID().toString();
                    System.out.println("send to server;      msg = " + uuid);
                    channel.writeAndFlush(uuid);
                } else {
                    System.out.println("executor shutdown");
                    executor.shutdown();
                }
            }, 0, 1, TimeUnit.SECONDS);

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
