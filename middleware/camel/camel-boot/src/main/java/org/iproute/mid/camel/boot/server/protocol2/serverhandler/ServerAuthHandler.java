package org.iproute.mid.camel.boot.server.protocol2.serverhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.mid.camel.boot.server.protocol2.Before;
import org.iproute.mid.camel.boot.server.protocol2.SimpleProtocolDecoder;
import org.iproute.mid.camel.boot.server.protocol2.SimpleProtocolEncoder;

import java.util.UUID;

/**
 * ServerAuthHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class ServerAuthHandler extends SimpleChannelInboundHandler<String> {

    private String authUuid;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String uuid = UUID.randomUUID().toString();
        log.info("发送验证字符串|{}", uuid);
        ctx.writeAndFlush(uuid);
        // 验证字符串 是 客户段将字符串反转一下
        authUuid = StringUtils.reverse(uuid);
        ctx.fireChannelActive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (!StringUtils.equals(authUuid, msg)) {
            log.error("客户端{}验证失败，关闭客户端", ctx.name());
            ctx.close();
            return;
        }

        log.info("客户端验证成功");

        /*
         * 移除原有的验证需要的handler
         */
        ctx.pipeline().remove(Before.FIX_NAME);
        ctx.pipeline().remove(Before.STR_DO_NAME);
        ctx.pipeline().remove(Before.STR_EN_NAME);
        ctx.pipeline().remove(Before.AUTH_NAME);


        ctx.pipeline().addLast(new SimpleProtocolDecoder());
        ctx.pipeline().addLast(new SimpleProtocolEncoder());
        ctx.pipeline().addLast(new ServerMsgHandler());

        // fire !!! ctx.pipeline()
        ctx.pipeline().fireChannelActive();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }
}
