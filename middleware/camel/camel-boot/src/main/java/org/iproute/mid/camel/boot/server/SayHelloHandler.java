package org.iproute.mid.camel.boot.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * SayHelloHandler
 *
 * @author zhuzhenjie
 * @since 2022/7/22
 */
@Slf4j
public class SayHelloHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    @SuppressWarnings("all")
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channel active ;ctx.name is {}", ctx.name());

        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                if (ctx.isRemoved()) {
                    break;
                } else {
                    log.info("send hello world message ... ");
                    ctx.writeAndFlush(Unpooled.copiedBuffer("hello world".getBytes(StandardCharsets.UTF_8)));
                }
            }

        });

        thread.start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        // ctx.writeAndFlush(Unpooled.copiedBuffer("hello world".getBytes(StandardCharsets.UTF_8)));
        ByteBuf bf = (ByteBuf) msg;
        int i = bf.readableBytes();
        byte[] bytes = new byte[i];
        bf.readBytes(bytes);
        log.info("receive msg is ： {}", new String(bytes));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("exceptionCaught", cause);
    }
}
