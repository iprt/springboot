package org.iproute.springboot.service;

/**
 * EventService
 *
 * @author devops@kubectl.net
 * @since 2023/7/28
 */
public interface EventService {

    /**
     * 发布消息
     *
     * @param data the data
     */
    void publish(String data);

}
