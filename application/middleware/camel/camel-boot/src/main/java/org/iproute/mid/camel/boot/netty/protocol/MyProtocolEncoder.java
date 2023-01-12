package org.iproute.mid.camel.boot.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MyProtocolEncoder 编码器
 *
 * @author zhuzhenjie
 * @since 2022/8/7
 */
public class MyProtocolEncoder extends MessageToByteEncoder<MyProtocol> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyProtocol msg, ByteBuf out) throws Exception {

        out.writeInt(msg.getLen());

        out.writeBytes(msg.getContent());

    }
}
