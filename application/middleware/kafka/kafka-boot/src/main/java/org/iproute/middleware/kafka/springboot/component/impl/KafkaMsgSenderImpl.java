package org.iproute.middleware.kafka.springboot.component.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.middleware.kafka.springboot.constant.TopicConst;
import org.iproute.middleware.kafka.springboot.component.KafkaMsgSender;
import org.iproute.middleware.kafka.springboot.entities.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * KafkaProducerImpl
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class KafkaMsgSenderImpl implements KafkaMsgSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson GSON = new GsonBuilder().create();

    public void send(String msg) {
        Message message = Message.builder()
                .id(System.currentTimeMillis()).msg(msg).sendTime(new Date())
                .build();
        log.info("【++++++++++++++++++ message ：{}】", GSON.toJson(message));
        // 对 topic =  hello2 的发送消息
        kafkaTemplate.send(TopicConst.DEFAULT_TOPIC, GSON.toJson(message));
    }

    public void send(String topic, String msg) {
        if (StringUtils.isBlank(topic)) {
            log.error("topic can not be empty");
            return;
        }
        Message message = Message.builder()
                .id(System.currentTimeMillis())
                .msg(msg)
                .sendTime(new Date())
                .build();

        log.info("【++++++++++++++++++ topic : {} ,message ：{}】", topic, GSON.toJson(message));
        // 对 topic =  hello2 的发送消息
        kafkaTemplate.send(topic, GSON.toJson(message));
    }
}
