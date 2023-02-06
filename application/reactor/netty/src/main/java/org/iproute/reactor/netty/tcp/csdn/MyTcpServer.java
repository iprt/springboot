package org.iproute.reactor.netty.tcp.csdn;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpServer;

import java.security.cert.CertificateException;
import java.time.Duration;
import java.util.List;

/**
 * MyTcpServer
 * <a href="https://blog.csdn.net/MyronCham/article/details/117731622">...</a>
 *
 * @author zhuzhenjie
 * @since 2023/2/4
 */
@Slf4j
public class MyTcpServer {

    public static void main(String[] args) throws CertificateException {
        TcpServer.create().port(1551)
                .doOnConnection(MyTcpServer::onConnection)
                // 以阻塞的方式启动服务器，并等待其初始化完成
                .bindUntilJavaShutdown(Duration.ofSeconds(30), null);
    }


    private static void onConnection(Connection conn) {
        // conn.addHandlerLast()
        conn.addHandlerLast(new StringToIntegerDecoder());
        MyServerConnection myConn = new MyServerConnection(conn);
        log.info("new client connected {}", conn);
        myConn.handle();
    }

    public static class StringToIntegerDecoder extends ByteToMessageDecoder {
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            int n = in.readableBytes();
            if (in.readableBytes() > 0) {
                in.readBytes(n);
                // 将解码的结果存储在此
                out.add(n);
            }
        }
    }
}
