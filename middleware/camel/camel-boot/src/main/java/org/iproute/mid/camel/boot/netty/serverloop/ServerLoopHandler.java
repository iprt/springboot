package org.iproute.mid.camel.boot.netty.serverloop;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.utils.NettyUtils;

/**
 * ServerLoopHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/20
 */
@Slf4j
public class ServerLoopHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已连接|{}", NettyUtils.getRemoteInfo((SocketChannel) ctx.channel()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("接收到客户端的消息 | {}", msg);
    }
}
