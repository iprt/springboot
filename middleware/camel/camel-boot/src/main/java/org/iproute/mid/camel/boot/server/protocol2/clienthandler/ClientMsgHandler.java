package org.iproute.mid.camel.boot.server.protocol2.clienthandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.server.protocol2.SimpleProtocol;

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
}
