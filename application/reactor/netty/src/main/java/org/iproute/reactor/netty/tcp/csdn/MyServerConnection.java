package org.iproute.reactor.netty.tcp.csdn;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.time.Duration;

/**
 * MyServerConnection
 *
 * @author devops@kubectl.net
 * @since 2023/2/5
 */
@Slf4j
public class MyServerConnection {
    final Connection conn;

    public MyServerConnection(Connection conn) {
        this.conn = conn;
    }

    public void handle() {
        conn.inbound()
                // 从 StringToIntegerDecoder 接受到作为结果接收到的对象
                .receiveObject()
                .log("MyServerConnection")
                .delayElements(Duration.ofSeconds(1)) // 将下一个元素延迟处理1秒
                .doOnNext(s -> {
                    log.info("Current received and decoded element is :{}", s);
                })
                // 在收到5个元素后取消 （实际上断开了客户端的连接）
                .take(5)
                .flatMap(s ->
                        conn.outbound()
                                .sendString(
                                        Mono.just(String.format("byte count :%d", (Integer) s))
                                )
                                .then()
                )
                // 必须按顺序使用该核心订阅者，以使连接在take(5) 之后断开连接
                .subscribe(conn.disposeSubscriber());
    }
}
