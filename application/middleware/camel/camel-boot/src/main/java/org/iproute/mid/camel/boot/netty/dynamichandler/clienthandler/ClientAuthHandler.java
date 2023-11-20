package org.iproute.mid.camel.boot.netty.dynamichandler.clienthandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.mid.camel.boot.netty.dynamichandler.AuthBefore;
import org.iproute.mid.camel.boot.netty.dynamichandler.protocol.MsgDecoder;
import org.iproute.mid.camel.boot.netty.dynamichandler.protocol.MsglEncoder;

/**
 * ClientAuthHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class ClientAuthHandler extends SimpleChannelInboundHandler<String> {

    private int authTime;

    public ClientAuthHandler() {
        this.authTime = 1;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        if (this.authTime == 1) {
            log.info("第一次验证，接收到验证字符串|{}", msg);

            log.info("处理第一次的验证字符串并发送给服务端");
            String authRsp = StringUtils.reverse(msg);
            // 测试验证失败
            // String authRsp = msg;
            ctx.writeAndFlush(authRsp);

            this.authTime++;
            return;
        }

        if (this.authTime == 2) {
            log.info("第二次验证，接收到验证字符串|{}", msg);

            log.info("处理第二次的验证字符串并发送给服务端");
            String authRsp = msg.toLowerCase();
            ctx.writeAndFlush(authRsp);
            this.authTime++;
        }

        // 第二次验证，无论服务端是否验证成功，客户端都都不再需要验证的handler

        // 如果服务端还有验证的话，可以尝试用 Promise
        ctx.pipeline().remove(AuthBefore.FIX_NAME);
        ctx.pipeline().remove(AuthBefore.STR_DO_NAME);
        ctx.pipeline().remove(AuthBefore.STR_EN_NAME);
        ctx.pipeline().remove(this);

        ctx.pipeline().addLast(new MsgDecoder());
        ctx.pipeline().addLast(new MsglEncoder());
        ctx.pipeline().addLast(new ClientMsgHandler());

        ctx.pipeline().fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("验证失败，服务端关闭连接");
    }
}
