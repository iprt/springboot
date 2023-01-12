package org.iproute.mid.camel.boot.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.springframework.stereotype.Service;

/**
 * MessageService
 *
 * @author zhuzhenjie
 * @since 2022/7/22
 */
@Service("messageService")
@Slf4j
public class MessageService {

    @Handler
    public void receiveFromTCP(final Exchange exchange) {
        final String messageFromTcp = exchange.getIn().getBody(String.class);
        log.info("[Service] - Message Received from TCP Channel --> {}", messageFromTcp);
    }
}
