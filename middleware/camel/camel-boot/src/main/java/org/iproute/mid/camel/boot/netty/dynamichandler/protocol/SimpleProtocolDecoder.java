package org.iproute.mid.camel.boot.netty.dynamichandler.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * MyProtocolDecoder 解码器
 * <p>
 * 参考文章 <a href="https://www.cnblogs.com/caoweixiong/p/14663492.html">...</a>
 *
 * @author zhuzhenjie
 * @since 2022/8/7
 */
public class SimpleProtocolDecoder extends ByteToMessageDecoder {

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

        out.add(SimpleProtocol.builder().len(len)
                .content(content)
                .build());

    }
}
