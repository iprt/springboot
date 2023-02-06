package org.iproute.springboot.intergration.tcpserver;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.ip.dsl.Tcp;
import org.springframework.integration.ip.tcp.serializer.TcpCodecs;

// 构造器注入
@AllArgsConstructor
@EnableIntegration
@Configuration
public class IntegrationConfig {
    private final TcpServerProps props;

    private final MessageTransformer messageTransformer;

    @Bean
    public IntegrationFlow integrationFlow() {
        return IntegrationFlows.from(
                        Tcp.inboundGateway(
                                Tcp.nioServer(this.props.getPort())
                                        .deserializer(TcpCodecs.crlf())
                                        .get())
                )
                .transform(Transformers.objectToString())
                .transform(this.messageTransformer)
                .get();
    }
}
