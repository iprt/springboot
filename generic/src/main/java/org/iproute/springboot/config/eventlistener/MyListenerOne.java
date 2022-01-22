package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;

/**
 * MyListenerOne
 *
 * @author winterfell
 * @since 2022/1/23
 */
@Slf4j
public class MyListenerOne implements ApplicationListener<MyEvent> {

    /**
     * 编写处理事件的逻辑
     *
     * @param event 当前事件对象
     */
    @Override
    public void onApplicationEvent(MyEvent event) {
        log.info("线程-【{}】 => 监听器-【MyListenerOne】 => 监听到的事件-【{}】", Thread.currentThread().getName(), event);
    }
}
