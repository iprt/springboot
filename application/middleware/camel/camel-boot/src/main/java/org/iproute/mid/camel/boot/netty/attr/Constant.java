package org.iproute.mid.camel.boot.netty.attr;

import io.netty.util.AttributeKey;

/**
 * AttributeMapConstant
 *
 * @author zhuzhenjie
 * @since 4/29/2023
 */
public class Constant {
    public static final AttributeKey<NettyChannel> NETTY_CHANNEL_KEY = AttributeKey.valueOf("netty.channel");
}
