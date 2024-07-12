package org.iproute.mid.camel.boot.netty.dynamichandler.serverhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.iproute.mid.camel.boot.netty.dynamichandler.AuthBefore;

import java.util.UUID;

/**
 * ServerChannelInitializer
 *
 * @author tech@intellij.io
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new LoggingHandler(LogLevel.DEBUG));

        ch.pipeline().addLast(AuthBefore.FIX_NAME, new FixedLengthFrameDecoder(
                UUID.randomUUID().toString().getBytes().length
        ));

        ch.pipeline().addLast(AuthBefore.STR_DO_NAME, new StringDecoder());

        ch.pipeline().addLast(AuthBefore.STR_EN_NAME, new StringEncoder());

        ch.pipeline().addLast(AuthBefore.AUTH_NAME, new ServerAuthHandler());

    }
}
