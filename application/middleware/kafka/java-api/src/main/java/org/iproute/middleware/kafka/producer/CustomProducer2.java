package org.iproute.middleware.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 不带回调函数的api
 *
 * @author tech@intellij.io
 * @since 2022/3/13
 */
public class CustomProducer2 {

    public static void main(String[] args) throws Exception {
        Properties props = new Properties();

        // kafka集群 broker list
        props.put("bootstrap.servers", "shdq02:9092");

        props.put("acks", "all");

        // 重试次数
        props.put("retries", 1);

        // 批次大小
        props.put("batch.size", 16384);

        // 等待时间
        props.put("linger.ms", 1);

        //RecordAccumulator 缓冲区大小
        props.put("buffer.memory", 33554432);

        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");


        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 100; i++) {
            producer.send(
                    new ProducerRecord<>("first", Integer.toString(i), Integer.toString(i)),
                    (metadata, exception) -> {
                        if (null == exception) {
                            System.out.println("success ---> offset = " + metadata.offset());
                        } else {
                            exception.printStackTrace();
                        }
                    }
            );
        }

        producer.close();
    }
}
