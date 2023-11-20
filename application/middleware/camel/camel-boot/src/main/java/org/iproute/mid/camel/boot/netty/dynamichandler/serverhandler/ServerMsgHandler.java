package org.iproute.mid.camel.boot.netty.dynamichandler.serverhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.dynamichandler.protocol.Msg;
import org.iproute.mid.camel.boot.netty.utils.NettyUtils;

/**
 * ServerMsgHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class ServerMsgHandler extends SimpleChannelInboundHandler<Msg> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ServerMsgHandler channelActive | {}", NettyUtils.getRemoteInfo(ctx));
        log.info("id is {}", ctx.channel().id());

        String msg = "Netty,Rock!";

        Msg pMsg = Msg.builder()
                .len(msg.getBytes().length)
                .content(msg.getBytes())
                .build();

        ctx.writeAndFlush(pMsg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        String clientMsg = new String(msg.getContent());
        log.info("接收到客户端【{}】消息|{}", NettyUtils.getRemoteInfo(ctx), clientMsg);

        String rspMsg = "server response ： " + clientMsg;
        Msg pRspMsg = Msg.builder()
                .len(rspMsg.getBytes().length)
                .content(rspMsg.getBytes())
                .build();

        ctx.writeAndFlush(pRspMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ServerMsgHandler exceptionCaught|{}", NettyUtils.getRemoteInfo(ctx));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // log.info("channelInactive|{}", channel.remoteAddress().toString());
        log.error("客户端断开连接|{}", NettyUtils.getRemoteInfo(ctx));
    }

}
