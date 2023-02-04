package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;
import reactor.netty.tcp.TcpSslContextSpec;

import java.io.File;

/**
 * Demo10_SSl_TLS
 *
 * @author winterfell
 * @since 2023/2/5
 */
public class Demo10_SSl_TLS {
    public static void main(String[] args) {
        File cert = new File("certificate.crt");
        File key = new File("private.key");

        TcpSslContextSpec tcpSslContextSpec = TcpSslContextSpec.forServer(cert, key);

        DisposableServer server =
                TcpServer.create()
                        .secure(spec -> spec.sslContext(tcpSslContextSpec))
                        .bindNow();


        server.onDispose().block();
    }
}
