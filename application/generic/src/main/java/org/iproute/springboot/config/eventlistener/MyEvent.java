package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * MyEvent
 *
 * @author winterfell
 * @since 2022/1/23
 */
@Slf4j
public class MyEvent extends ApplicationEvent {


    /**
     * 构造器
     *
     * @param source 该事件的相关数据
     * @since 2019 /11/19 6:40
     */
    public MyEvent(Object source) {
        super(source);
    }
}
