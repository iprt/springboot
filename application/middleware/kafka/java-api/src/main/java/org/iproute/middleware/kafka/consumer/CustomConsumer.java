package org.iproute.middleware.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * 自动提交offset
 *
 * @author winterfell
 * @since 2022/3/15
 */
public class CustomConsumer {

    public static void main(String[] args) {
        Properties props = new Properties();

        props.put("bootstrap.servers", "shdq02:9092");
        // 消费者组，只要 group.id 相同，就属于同一个消费者组
        props.put("group.id", "test");

        // =============================================================
        // 自动提交 offset
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        // =============================================================


        props.put("key.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer",
                "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("first"));

        while (true) {
            //消费者拉取数据
            ConsumerRecords<String, String> records =
                    consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value  = %s%n", record.offset(), record.key(), record.value());
            }
            //同步提交，当前线程会阻塞直到 offset 提交成功
        }

    }

}
