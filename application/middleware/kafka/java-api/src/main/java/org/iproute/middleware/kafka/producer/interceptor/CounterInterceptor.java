package org.iproute.middleware.kafka.producer.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * CounterInterceptor
 *
 * @author devops@kubectl.net
 * @since 2022/3/16
 */
public class CounterInterceptor implements ProducerInterceptor<String, String> {

    private int successCounter = 0;
    private int failureCounter = 0;

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        if (null == exception) {
            successCounter++;
        } else {
            failureCounter++;
        }

    }

    @Override
    public void close() {
        System.out.println("Success send : " + successCounter);
        System.out.println("Failure send : " + failureCounter);
    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
