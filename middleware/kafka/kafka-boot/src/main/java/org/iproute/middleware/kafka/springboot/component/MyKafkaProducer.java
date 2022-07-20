package org.iproute.middleware.kafka.springboot.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.middleware.kafka.springboot.entities.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * KafkaSender
 *
 * @author winterfell
 * @since 2022/3/16
 */
@Component
@Slf4j
public class MyKafkaProducer {
    public static final String DEFAULT_TOPIC = "hello";
    private final KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    public MyKafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String msg) {
        Message message = new Message();

        message.setId(System.currentTimeMillis());
        message.setMsg(msg);
        message.setSendTime(new Date());
        log.info("【++++++++++++++++++ message ：{}】", gson.toJson(message));
        //对 topic =  hello2 的发送消息
        kafkaTemplate.send(DEFAULT_TOPIC, gson.toJson(message));
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

        log.info("【++++++++++++++++++ topic : {} ,message ：{}】", topic, gson.toJson(message));
        //对 topic =  hello2 的发送消息
        kafkaTemplate.send(topic, gson.toJson(message));
    }
}
