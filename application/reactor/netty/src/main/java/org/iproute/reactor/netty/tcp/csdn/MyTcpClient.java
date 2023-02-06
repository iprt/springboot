package org.iproute.reactor.netty.tcp.csdn;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.concurrent.CountDownLatch;

/**
 * TcpClient
 *
 * @author zhuzhenjie
 * @since 2023/2/4
 */
@Slf4j
public class MyTcpClient {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        StringBuilder toSend = new StringBuilder("a");

        TcpClient.create()
                .port(1551)
                .doOnConnected(conn -> {
                    log.info("client connect successful");
                    conn.outbound()
                            .sendString(Mono.just(toSend.toString()))
                            .then(conn.inbound()
                                    .receive()
                                    .asString()
                                    .log("tcp-connection")
                                    .doOnNext(s ->
                                            log.info("Server returned " + s)
                                    )
                                    .flatMap(s -> conn
                                            .outbound()
                                            .sendString(
                                                    Mono.just(toSend.append("a").toString())
                                            )
                                    )
                                    .then()
                            )
                            .then()
                            .subscribe();
                })
                .doOnDisconnected(conn -> {
                    log.error("Server disconnected");
                    latch.countDown();
                })
                .connect()
                .log("tcp-client")
                .doOnError(e ->
                        log.error("Error connecting to server ... ", e)
                )
                .subscribe();

        latch.await();
    }
}
