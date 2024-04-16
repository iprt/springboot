package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * CustomEventListenerFirst
 *
 * @author devops@kubectl.net
 * @since 2022/1/23
 */
@Slf4j
public class CustomEventListenerFirst implements ApplicationListener<CustomEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event 当前事件对象
     */
    @Override
    public void onApplicationEvent(CustomEvent event) {
        log.info("监听器线程 : {}", Thread.currentThread().getName());
        log.info("监听器(MyEventListenerOne) => 监听到的事件: {}", event);
    }
}
