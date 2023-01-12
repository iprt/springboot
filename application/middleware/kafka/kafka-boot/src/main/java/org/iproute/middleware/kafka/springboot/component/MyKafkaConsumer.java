package org.iproute.middleware.kafka.springboot.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * KafkaConsumer
 *
 * @author winterfell
 * @since 2022/3/16
 */
@Component
@Slf4j
public class MyKafkaConsumer {

    @KafkaListener(topics = {"hello"})
    public void listen(ConsumerRecord<String, String> record) {
        Optional.ofNullable(record.value())
                .ifPresent(message -> {
                    log.info("【+++++++++++++++++ record = {} 】", record);
                    log.info("【+++++++++++++++++ message = {}】", message);
                });
    }
}
