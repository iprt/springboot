package org.iproute.mid.camel.boot.netty.serverloopsend;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.utils.NettyUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ServerLoopHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/20
 */
@Slf4j
public class LoopServerHandler extends SimpleChannelInboundHandler<String> {

    private static final ScheduledExecutorService ses = Executors.newScheduledThreadPool(10);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已连接|{}", NettyUtils.getRemoteInfo((SocketChannel) ctx.channel()));
        // 这个地方需要不断的发送消息 ?


        AtomicLong i = new AtomicLong(1);
        ses.schedule(new Runnable() {
            @Override
            public void run() {
                if (ctx.channel().isActive()) {
                    ctx.writeAndFlush("Hello World " + i.getAndIncrement());
                    ses.schedule(this, 1, TimeUnit.SECONDS);
                }
            }
        }, 1, TimeUnit.SECONDS);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("接收到客户端的消息 | {}", msg);
    }
}
