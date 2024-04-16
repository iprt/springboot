package org.iproute.mid.camel.boot.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * MyProtocolEncoder 编码器
 *
 * @author devops@kubectl.net
 * @since 2022/8/7
 */
public class MyProtocolEncoder extends MessageToByteEncoder<MyMsg> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MyMsg msg, ByteBuf out) throws Exception {

        // out.writeInt(msg.getLen());
        //
        // out.writeBytes(msg.getContent());


        byte[] bytes = msg.getData().getBytes();

        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }
}
