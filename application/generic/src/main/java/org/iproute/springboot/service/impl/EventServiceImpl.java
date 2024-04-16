package org.iproute.springboot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.eventlistener.CustomEvent;
import org.iproute.springboot.config.atx.ApplicationContextUtils;
import org.iproute.springboot.service.EventService;
import org.springframework.stereotype.Service;

/**
 * EventServiceImpl
 *
 * @author devops@kubectl.net
 * @since 2023/7/28
 */
@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Override
    public void publish(String data) {
        log.info("发布事件 : {}", data);
        ApplicationContextUtils.getApplicationContext().publishEvent(
                new CustomEvent(data)
        );
    }
}
