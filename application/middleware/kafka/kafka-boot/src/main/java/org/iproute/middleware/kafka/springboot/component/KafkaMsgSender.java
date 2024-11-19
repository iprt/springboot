package org.iproute.middleware.kafka.springboot.component;

/**
 * KafkaSender
 *
 * @author tech@intellij.io
 * @since 2022/3/16
 */
public interface KafkaMsgSender {

    /**
     * Sends a message to a Kafka topic (default topic is hello).
     *
     * @param msg the message to be sent
     */
    void send(String msg);

    /**
     * Sends a message to a Kafka topic.
     *
     * @param topic the topic to send the message to
     * @param msg   the message to be sent
     */
    void send(String topic, String msg);

}
