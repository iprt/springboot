package org.iproute.mid.camel.boot.netty.attr.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.utils.NettyUtils;
import org.iproute.mid.camel.boot.netty.utils.RemoteInfo;

/**
 * AttrServerHandler
 *
 * @author zhuzhenjie
 * @since 4/28/2023
 */
@Slf4j
public class AttrServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("AttrServerHandler channel active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("receive msg : {}", msg);
        ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        RemoteInfo remoteInfo = NettyUtils.getRemoteInfo(ctx);
        log.error("客户端断开连接 {}", remoteInfo);
        ctx.close();
    }
}
