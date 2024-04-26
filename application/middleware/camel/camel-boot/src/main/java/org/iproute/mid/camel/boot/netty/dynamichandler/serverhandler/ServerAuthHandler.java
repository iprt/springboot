package org.iproute.mid.camel.boot.netty.dynamichandler.serverhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.mid.camel.boot.netty.dynamichandler.AuthBefore;
import org.iproute.mid.camel.boot.netty.dynamichandler.protocol.MsgDecoder;
import org.iproute.mid.camel.boot.netty.dynamichandler.protocol.MsgEncoder;
import org.iproute.mid.camel.boot.netty.utils.NettyUtils;

import java.util.UUID;

/**
 * ServerAuthHandler
 *
 * @author devops@kubectl.net
 * @since 2022/8/19
 */
@Slf4j
public class ServerAuthHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 验证的次数
     */
    private int authTime;
    private String firstAuthUUID;

    private String secondAuthUUID;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String uuid = UUID.randomUUID().toString();
        log.info("客户端建立连接|{}", NettyUtils.getRemoteInfo(ctx));
        log.info("发送第一次验证字符串|{}", uuid);
        ctx.writeAndFlush(uuid);
        this.authTime = 1;

        // 验证字符串的方法： 客户段将字符串反转一下
        firstAuthUUID = StringUtils.reverse(uuid);
        ctx.fireChannelActive();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        // 第一次验证
        if (authTime == 1) {

            if (!StringUtils.equals(firstAuthUUID, msg)) {
                log.error("客户端第一次验证失败，关闭客户端|{}", NettyUtils.getRemoteInfo(ctx));
                ctx.close();
                return;
            }


            log.info("客户端第一次验证成功，客户端|{}", NettyUtils.getRemoteInfo(ctx));

            this.secondAuthUUID = UUID.randomUUID().toString();
            // 第二次验证需要发过去的字符串变成小写
            log.info("发送第二次验证字符串|{}", secondAuthUUID.toUpperCase());
            ctx.writeAndFlush(secondAuthUUID.toUpperCase());
            this.authTime++;
            return;
        }

        // 第二次验证
        if (authTime == 2) {

            if (!StringUtils.equals(secondAuthUUID, msg)) {
                log.error("客户端第二次验证失败，关闭客户端|{}", NettyUtils.getRemoteInfo(ctx));
                ctx.close();
                return;
            }

            log.info("客户端第二次验证成功");
        }

        /*
         * 移除原有的验证需要的handler
         */
        ctx.pipeline().remove(AuthBefore.FIX_NAME);
        ctx.pipeline().remove(AuthBefore.STR_DO_NAME);
        ctx.pipeline().remove(AuthBefore.STR_EN_NAME);
        ctx.pipeline().remove(AuthBefore.AUTH_NAME);


        ctx.pipeline().addLast(new MsgDecoder());
        ctx.pipeline().addLast(new MsgEncoder());
        ctx.pipeline().addLast(new ServerMsgHandler());

        // fire !!! ctx.pipeline()
        ctx.pipeline().fireChannelActive();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ServerAuthHandler exceptionCaught | Server is = {}", NettyUtils.getRemoteInfo(ctx));
        ctx.close();
    }
}
