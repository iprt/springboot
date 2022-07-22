package org.iproute.mid.camel.boot.router;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Netty4TcpRouter
 *
 * @author zhuzhenjie
 * @since 2022/7/22
 */
@Component
@Slf4j
public class Netty4TcpRouter extends RouteBuilder {

    /**
     * netty router 的配置
     *
     * <a href="https://camel.apache.org/components/3.17.x/netty-component.html">...</a>
     */
    @Override
    public void configure() throws Exception {
        // sync = false 之后 NettyServer 不在接收消息
        this.from("netty:tcp://localhost:7000?encoding=utf8&clientMode=true&sync=false").routeId("tcp-client")
                .process(exchange -> log.info("[Processor] - Incoming Message -> {}", exchange.getIn().getBody(String.class)))
                .to("bean:messageService");
    }
}
