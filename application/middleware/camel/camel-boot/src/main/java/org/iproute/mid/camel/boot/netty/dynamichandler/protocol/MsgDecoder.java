package org.iproute.mid.camel.boot.netty.dynamichandler.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * MsgDecoder 解码器
 * <p>
 * 参考文章 <a href="https://www.cnblogs.com/caoweixiong/p/14663492.html">...</a>
 *
 * @author tech@intellij.io
 * @since 2022/8/7
 */
public class MsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        in.markReaderIndex(); // 标记 ByteBuf 读指针位置

        int len = in.readInt();

        if (in.readableBytes() < len) {

            in.resetReaderIndex(); // 重置 ByteBuf 读指针位置

            return;
        }

        byte[] content = new byte[len];
        in.readBytes(content);

        out.add(Msg.builder().len(len)
                .content(content)
                .build());

    }
}

/*

markReaderIndex和resetReaderIndex是一个成对的操作。

markReaderIndex可以打一个标记，
调用resetReaderIndex可以把readerIndex重置到原来打标记的位置。

 */
