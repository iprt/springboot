package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * MySyncListener
 *
 * @author zhuzhenjie
 * @since 2022/1/23
 */
@Component
@Slf4j
public class MyEventSyncListener implements ApplicationListener<MyEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event 当前事件对象
     */
    @Override
    @Async
    public void onApplicationEvent(MyEvent event) {
        log.info("监听器线程 : {}", Thread.currentThread().getName());
        log.info("监听器(MyEventSyncListener) => 监听到的事件: {}", event);
    }
}
