package org.iproute.middleware.mqtt.springboot.config;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * MqttClientProperties
 *
 * @author tech@intellij.io
 * @since 2025-05-29
 */
@ConfigurationProperties(prefix = "spring.mqtt.client")
@Data
public class MqttClientProperties {

    @NotBlank(message = "brokerHost must not be blank")
    private String brokerHost;

    @NotNull(message = "brokerPort must not be null")
    @Min(value = 1, message = "brokerPort must be greater than or equal to 1")
    @Max(value = 65535, message = "brokerPort must be less than or equal to 65535")
    private Integer brokerPort;

    @NotBlank(message = "clientId must not be blank")
    private String clientId;

    private String username;
    private String password;
}
