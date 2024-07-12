package org.iproute.middleware.kafka.producer.interceptor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * InterceptorProducer
 *
 * @author tech@intellij.io
 * @since 2022/3/16
 */
public class InterceptorProducer {

    public static void main(String[] args) {

        // 1 设置配置信息
        Properties props = new Properties();
        props.put("bootstrap.servers", "shdq02:9092");
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer",
                "org.apache.kafka.common.serialization.StringSerializer");


        List<String> interceptors = new ArrayList<>();
        interceptors.add("org.iproute.middleware.kafka.producer.interceptor.TimeInterceptor");
        interceptors.add("org.iproute.middleware.kafka.producer.interceptor.CounterInterceptor");

        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);

        Producer<String, String> producer = new KafkaProducer<>(props);

        String topic = "first";
        for (int i = 0; i < 10; i++) {

            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "message " + i);
            producer.send(record);

        }

        producer.close();
    }

}
