package org.iproute.mid.camel.boot.netty.dynamichandler.clienthandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.dynamichandler.SimpleProtocol;

/**
 * ClientMsgHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class ClientMsgHandler extends SimpleChannelInboundHandler<SimpleProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("ClientMsgHandler channelActive");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SimpleProtocol msg) throws Exception {
        String receiveMsg = new String(msg.getContent());
        log.info("接收到服务端的消息|{}", receiveMsg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ClientMsgHandler exceptionCaught");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel sc = (SocketChannel) ctx.channel();
        log.error("服务端【{}】断开连接", sc.remoteAddress().toString());
    }
}
