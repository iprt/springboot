package org.iproute.middleware.mqtt.springboot.service;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static org.iproute.middleware.mqtt.springboot.MqttClientApplication.Profile.DEV;

/**
 * MqttService
 *
 * @author tech@intellij.io
 * @since 2025-05-29
 */
public interface MqttService {
    /**
     * Publish a message to a topic, with a default QoS of 0
     *
     * @param topic   the topic to publish to
     * @param payload the message to publish
     */
    void publish(String topic, String payload);

    void publish(String topic, String payload, MqttQos qos);

    @Profile(DEV)
    @Service
    @Slf4j
    class DefaultMqttService implements MqttService {

        @Override
        public void publish(String topic, String payload) {
            log.info("publish | topic: {}, payload: {}", topic, payload);
        }

        @Override
        public void publish(String topic, String payload, MqttQos qos) {
            log.info("publish | topic: {}, payload: {}, qos: {}", topic, payload, qos);
        }
    }

}
