package org.iproute.springboot.intergration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;

/**
 * IntegrationConfig
 *
 * @author winterfell
 * @since 2022/1/21
 */
@EnableIntegration
@Configuration
public class IntegrationConfig {

    private final TcpServerProps props;

    private final MessageTransformer messageTransformer;

    public IntegrationConfig(TcpServerProps props, MessageTransformer messageTransformer) {
        this.props = props;
        this.messageTransformer = messageTransformer;
    }

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(
                        Tcp.inboundGateway(Tcp.nioServer(this.props.getPort())
                                .deserializer(TcpCodecs.crlf())
                                .get())
                )
                .transform(Transformers.objectToString())
                .transform(this.messageTransformer)
                .get();
    }

}
