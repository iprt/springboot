package org.iproute.middleware.kafka.springboot.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.iproute.middleware.kafka.springboot.constant.GroupConst;
import org.iproute.middleware.kafka.springboot.constant.TopicConst;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * KafkaConsumer
 *
 * @author tech@intellij.io
 * @since 2022/3/16
 */
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = TopicConst.DEFAULT_TOPIC, groupId = GroupConst.DEFAULT_GROUP)
    public void listen(ConsumerRecord<String, String> record) {
        Optional.ofNullable(record.value())
                .ifPresent(message -> {
                    log.info("【+++++++++++++++++ record = {} 】", record);
                    log.info("【+++++++++++++++++ message = {}】", message);
                });
    }

}
