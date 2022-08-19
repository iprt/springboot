package org.iproute.mid.camel.boot.server.protocol2.serverhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.iproute.mid.camel.boot.server.protocol2.Before;

import java.util.UUID;

/**
 * ServerChannelInitializer
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));

        ch.pipeline().addLast(Before.FIX_NAME, new FixedLengthFrameDecoder(
                UUID.randomUUID().toString().getBytes().length
        ));

        ch.pipeline().addLast(Before.STR_DO_NAME, new StringDecoder());

        ch.pipeline().addLast(Before.STR_EN_NAME, new StringEncoder());

        ch.pipeline().addLast(Before.AUTH_NAME, new ServerAuthHandler());

    }
}
