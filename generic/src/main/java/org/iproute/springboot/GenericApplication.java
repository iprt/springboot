package org.iproute.springboot;

import org.iproute.springboot.config.eventlistener.MyEvent;
import org.iproute.springboot.config.eventlistener.MyListenerOne;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author winterfell
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class GenericApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(GenericApplication.class);
        application.addListeners(new MyListenerOne());
        ConfigurableApplicationContext applicationContext = application.run(args);
        testEvent(applicationContext);
    }

    private static void testEvent(ConfigurableApplicationContext applicationContext) {
        MyEvent event = new MyEvent("我是一只小小小小鸟~~~");
        applicationContext.publishEvent(event);
    }
}
