package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * MyListenerOne
 *
 * @author winterfell
 * @since 2022/1/23
 */
@Component
@Slf4j
public class MyListenerTwo implements ApplicationListener<MyEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event 当前事件对象
     */
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("线程-【{}】 => 监听器-【MyListenerTwo】 => 监听到的事件-【{}】", Thread.currentThread().getName(), event);
    }
}
