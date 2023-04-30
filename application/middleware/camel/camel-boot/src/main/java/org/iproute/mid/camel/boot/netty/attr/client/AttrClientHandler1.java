package org.iproute.mid.camel.boot.netty.attr.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.attr.NettyChannel;

import java.util.Date;

import static org.iproute.mid.camel.boot.netty.attr.Constant.NETTY_CHANNEL_KEY;

/**
 * AttrClientHandler
 *
 * @author zhuzhenjie
 * @since 4/28/2023
 */
@Slf4j
public class AttrClientHandler1 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);

        NettyChannel nettyChannel = attr.get();

        if (nettyChannel == null) {
            log.info("AttrClientHandler1 的 channelActive 中  Attribute 不存在值");
            NettyChannel newNettyChannel = NettyChannel.builder().name("AttrClientHandler1").date(new Date()).build();
            attr.setIfAbsent(newNettyChannel);
        } else {
            log.info("AttrClientHandler1 的 channelActive 中  Attribute 存在值");
            log.info("Attribute nettyChannel = {}", nettyChannel);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Attribute<NettyChannel> attr = ctx.channel().attr(NETTY_CHANNEL_KEY);

        NettyChannel nettyChannel = attr.get();

        if (nettyChannel == null) {
            log.info("AttrClientHandler1 的 channelRead 中  Attribute 不存在值");
            NettyChannel newNettyChannel = NettyChannel.builder().name("AttrClientHandler1").date(new Date()).build();
            attr.setIfAbsent(newNettyChannel);
        } else {
            log.info("AttrClientHandler1 的 channelRead 中  Attribute 存在值");
            log.info("Attribute nettyChannel = {}", nettyChannel);
        }
    }
}
