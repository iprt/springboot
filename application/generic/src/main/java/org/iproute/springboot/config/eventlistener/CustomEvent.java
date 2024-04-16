package org.iproute.springboot.config.eventlistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 * 自定义事件
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class CustomEvent extends ApplicationEvent {

    /**
     * 构造器
     *
     * @param source 该事件的相关数据
     * @since 2019 /11/19 6:40
     */
    public CustomEvent(Object source) {
        super(source);
    }
}
