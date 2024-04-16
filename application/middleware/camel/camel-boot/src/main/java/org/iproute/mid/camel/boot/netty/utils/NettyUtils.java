package org.iproute.mid.camel.boot.netty.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;

/**
 * NettyUtils
 *
 * @author devops@kubectl.net
 * @since 2022/8/20
 */
public class NettyUtils {

    private NettyUtils() {
    }

    /**
     * Gets remote info.
     *
     * @param channel the channel
     * @return the remote info
     */
    public static RemoteInfo getRemoteInfo(SocketChannel channel) {
        InetSocketAddress remote = channel.remoteAddress();

        return RemoteInfo.builder()
                .host(remote.getHostString())
                .port(remote.getPort())
                .build();
    }

    /**
     * Gets remote info.
     *
     * @param ctx the ctx
     * @return the remote info
     */
    public static RemoteInfo getRemoteInfo(ChannelHandlerContext ctx) {
        return getRemoteInfo((SocketChannel) ctx.channel());
    }
}
