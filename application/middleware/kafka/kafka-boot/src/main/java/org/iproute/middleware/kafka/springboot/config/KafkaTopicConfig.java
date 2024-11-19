package org.iproute.middleware.kafka.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.iproute.middleware.kafka.springboot.constant.TopicConst;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * KafkaTopicConfig
 *
 * @author tech@intellij.io
 */
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic defaultTopic() {
        return new NewTopic(TopicConst.DEFAULT_TOPIC, 1, (short) 1);
    }

}
