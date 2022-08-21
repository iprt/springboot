package org.iproute.mid.camel.boot.netty.serverloopsend;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.utils.NettyUtils;
import org.iproute.mid.camel.boot.netty.utils.RemoteInfo;

import static org.iproute.mid.camel.boot.netty.serverloopsend.LoopServerUtils.CLIENT_MAP;

/**
 * ServerLoopHandler
 *
 * @author zhuzhenjie
 * @since 2022/8/20
 */
@Slf4j
public class LoopServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已连接|{}", NettyUtils.getRemoteInfo((SocketChannel) ctx.channel()));
        RemoteInfo remote = NettyUtils.getRemoteInfo(ctx);
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText();

        CLIENT_MAP.put(channelId, LoopServerUtils.ChannelDetail.builder().host(remote.getHost()).port(remote.getPort()).channel(channel).build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("接收到客户端的消息 | {}", msg);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(cause.getMessage());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.error("客户端断开连接|{}", NettyUtils.getRemoteInfo(ctx));
        Channel channel = ctx.channel();
        String channelId = channel.id().asLongText();
        CLIENT_MAP.remove(channelId);
    }


}
