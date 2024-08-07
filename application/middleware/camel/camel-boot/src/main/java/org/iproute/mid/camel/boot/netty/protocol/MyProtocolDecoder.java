package org.iproute.mid.camel.boot.netty.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * MyProtocolDecoder 解码器
 * <p>
 * 参考文章 <a href="https://www.cnblogs.com/caoweixiong/p/14663492.html">...</a>
 *
 * @author tech@intellij.io
 * @since 2022/8/7
 */
public class MyProtocolDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        // 标记 ByteBuf 读指针位置
        in.markReaderIndex();

        int len = in.readInt();

        if (in.readableBytes() < len) {
            // 重置 ByteBuf 读指针位置
            in.resetReaderIndex();

            return;
        }

        byte[] content = new byte[len];
        in.readBytes(content);

        // out.add(MyProtocol.builder().len(len)
        //         .content(content)
        //         .build());

        out.add(MyMsg.builder()
                .success(true)
                .data(new String(content))
                .build());

    }
}
