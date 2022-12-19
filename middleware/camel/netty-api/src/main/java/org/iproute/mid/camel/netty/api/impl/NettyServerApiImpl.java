package org.iproute.mid.camel.netty.api.impl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.netty.api.NettyServerApi;
import org.iproute.mid.camel.netty.model.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * NettyServerApiImpl
 *
 * @author zhuzhenjie
 * @since 2022/12/19
 */
@Service
@Slf4j
public class NettyServerApiImpl implements NettyServerApi {

    private static final Map<Integer, Channel> channelMap = new ConcurrentHashMap<>();

    @Autowired
    private ExecutorService executorService;

    @Override
    public boolean start(ServerInfo serverInfo) {
        NettyStarter starter = new NettyStarter(serverInfo.getPort());
        executorService.execute(starter);
        return true;
    }

    private static class NettyStarter implements Runnable {
        private int port;

        public NettyStarter(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            NioEventLoopGroup boss = new NioEventLoopGroup(1);
            NioEventLoopGroup worker = new NioEventLoopGroup(4);

            ServerBootstrap server = new ServerBootstrap();

            server.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
                                    int i = buf.readableBytes();
                                    byte[] bytes = new byte[i];
                                    buf.readBytes(bytes);
                                    log.info("receive : {}", new String(bytes));

                                    buf.writeBytes(bytes);
                                    ctx.writeAndFlush(buf);
                                }
                            });
                        }
                    });

            try {
                log.info("Server start port at {}", port);
                Channel channel = server.bind(port).sync().channel();
                channelMap.put(port, channel);
                channel.closeFuture().sync();
            } catch (InterruptedException e) {
                log.error("server error", e);
            } finally {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        }
    }

    @Override
    public boolean stop(ServerInfo serverInfo) {
        Channel channel = channelMap.get(serverInfo.getPort());
        if (channel.isActive()) {
            channel.close();
            log.info("stop tcp server on port {}", serverInfo.getPort());
            return true;
        }
        return false;
    }
}
