package org.iproute.springboot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.config.aop.RecordParameters;
import org.iproute.springboot.config.mvc.anno.RequestLog;
import org.iproute.springboot.entities.po.EventMsg;
import org.iproute.springboot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * EventController
 *
 * @author devops@kubectl.net
 * @since 2023/7/28
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/event")
@Slf4j
@RecordParameters
public class EventController {
    private final EventService eventService;

    @RequestLog("发布事件")
    @PostMapping("/publish")
    public void publishEvent(@RequestBody EventMsg eventMsg) {
        eventService.publish(eventMsg.getData());
    }
}
