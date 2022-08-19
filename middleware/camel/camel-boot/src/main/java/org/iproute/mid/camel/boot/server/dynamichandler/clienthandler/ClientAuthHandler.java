package org.iproute.mid.camel.boot.server.dynamichandler.clienthandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.mid.camel.boot.server.dynamichandler.Before;
import org.iproute.mid.camel.boot.server.dynamichandler.SimpleProtocolDecoder;
import org.iproute.mid.camel.boot.server.dynamichandler.SimpleProtocolEncoder;

/**
 * ClientAuthHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class ClientAuthHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("接收到验证字符串|{}", msg);
        String authRsp = StringUtils.reverse(msg);
        ctx.writeAndFlush(authRsp);

        // 如果服务端还有验证的话，可以尝试用 Promise
        ctx.pipeline().remove(Before.FIX_NAME);
        ctx.pipeline().remove(Before.STR_DO_NAME);
        ctx.pipeline().remove(Before.STR_EN_NAME);
        ctx.pipeline().remove(this);

        ctx.pipeline().addLast(new SimpleProtocolDecoder());
        ctx.pipeline().addLast(new SimpleProtocolEncoder());
        ctx.pipeline().addLast(new ClientMsgHandler());

        ctx.pipeline().fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("验证失败，服务端关闭连接");
    }
}
