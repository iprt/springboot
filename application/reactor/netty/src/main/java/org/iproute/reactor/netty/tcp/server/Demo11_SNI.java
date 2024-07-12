package org.iproute.reactor.netty.tcp.server;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import javax.net.ssl.SSLException;
import java.io.File;

/**
 * Demo11_ServerNameIndication
 *
 * @author tech@intellij.io
 * @since 2023/2/5
 */
public class Demo11_SNI {

    public static void main(String[] args) throws SSLException {
        File defaultCert = new File("default_certificate.crt");
        File defaultKey = new File("default_private.key");

        File testDomainCert = new File("default_certificate.crt");
        File testDomainKey = new File("default_private.key");

        SslContext defaultSslContext = SslContextBuilder.forServer(defaultCert, defaultKey).build();
        SslContext testDomainSslContext = SslContextBuilder.forServer(testDomainCert, testDomainKey).build();

        DisposableServer server =
                TcpServer.create()
                        .secure(spec -> spec.sslContext(defaultSslContext)
                                .addSniMapping("*.test.com",
                                        testDomainSpec -> testDomainSpec.sslContext(testDomainSslContext)))
                        .bindNow();

        server.onDispose()
                .block();
    }
}
