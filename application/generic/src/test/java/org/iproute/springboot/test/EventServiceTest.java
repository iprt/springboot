package org.iproute.springboot.test;

import org.iproute.springboot.service.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * EventServiceTest
 *
 * @author devops@kubectl.net
 * @since 2023/7/28
 */
@SpringBootTest
public class EventServiceTest {

    private static final String MSG = "Hello,this is an test event";

    @Autowired
    private EventService eventService;

    @Test
    public void testPublishEvent() {
        eventService.publish(MSG);
    }

}
