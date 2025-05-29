package org.iproute.middleware.mqtt.springboot.config;

import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static org.iproute.middleware.mqtt.springboot.MqttClientApplication.Profile.PROD;

/**
 * MqttConfig
 *
 * @author tech@intellij.io
 * @since 2025-05-29
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Configuration
@Profile(PROD)
@Slf4j
public class MqttConfig {
    private final MqttClientProperties mqttClientProperties;

    @Bean(destroyMethod = "disconnect")
    public Mqtt5BlockingClient mqtt5BlockingClient() {
        Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier(mqttClientProperties.getClientId())
                .serverHost(mqttClientProperties.getBrokerHost())
                .serverPort(mqttClientProperties.getBrokerPort())
                .simpleAuth()
                .username(mqttClientProperties.getUsername()).password(mqttClientProperties.getPassword().getBytes())
                .applySimpleAuth()
                .automaticReconnectWithDefaultConfig()
                .buildBlocking();

        Mqtt5ConnAck connect = client.connect();
        log.info("mqtt connect: {}", connect);
        return client;
    }

}
