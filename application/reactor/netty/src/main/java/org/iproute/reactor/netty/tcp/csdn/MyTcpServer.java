package org.iproute.reactor.netty.tcp.csdn;

import reactor.netty.Connection;
import reactor.netty.tcp.TcpServer;

import java.security.cert.CertificateException;
import java.time.Duration;

/**
 * MyTcpServer
 * <a href="https://blog.csdn.net/MyronCham/article/details/117731622">...</a>
 *
 * @author winterfell
 * @since 2023/2/4
 */
public class MyTcpServer {

    public static void main(String[] args) throws CertificateException {

    }


    private static void onConnection(Connection conn) {
        // conn.addHandlerLast()
    }

}
