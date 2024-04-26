package org.iproute.mid.camel.boot.netty.dynamichandler.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MsgEncoder 编码器
 *
 * @author devops@kubectl.net
 * @since 2022/8/7
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Msg msg, ByteBuf out) throws Exception {

        out.writeInt(msg.getLen());

        out.writeBytes(msg.getContent());

    }
}
