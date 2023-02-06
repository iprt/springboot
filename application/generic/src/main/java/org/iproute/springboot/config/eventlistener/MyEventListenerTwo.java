package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * MyListenerOne
 *
 * @author zhuzhenjie
 * @since 2022/1/23
 */
@Component
@Slf4j
public class MyEventListenerTwo implements ApplicationListener<MyEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event 当前事件对象
     */
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("监听器 - 线程名称 : {}", Thread.currentThread().getName());
        log.info("监听器 - MyEventListenerTwo => 监听到的事件: {}", event);
    }
}
