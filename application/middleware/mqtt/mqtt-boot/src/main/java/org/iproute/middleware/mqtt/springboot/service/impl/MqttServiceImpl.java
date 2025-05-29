package org.iproute.middleware.mqtt.springboot.service.impl;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.middleware.mqtt.springboot.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static org.iproute.middleware.mqtt.springboot.MqttClientApplication.Profile.PROD;

/**
 * MqttServiceImpl
 *
 * @author tech@intellij.io
 * @since 2025-05-29
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Profile(PROD)
@Service
@Slf4j
public class MqttServiceImpl implements MqttService {
    private final Mqtt5BlockingClient mqtt5BlockingClient;

    @Override
    public void publish(String topic, String payload) {
        this.publish(topic, payload, MqttQos.AT_MOST_ONCE);
    }

    @Override
    public void publish(String topic, String payload, MqttQos qos) {
        if (StringUtils.isBlank(topic) || StringUtils.isBlank(payload) || qos == null) {
            log.error("publish | topic or payload or qos is blank");
            return;
        }

        log.info("publish topic: {}, payload: {}", topic, payload);

        Mqtt5PublishResult send = mqtt5BlockingClient.publishWith()
                .topic(topic)
                .qos(qos)
                .payload(payload.getBytes())
                .send();

        send.getError().ifPresent(e -> log.error("publish | error: {}", e.getMessage()));
    }
}
