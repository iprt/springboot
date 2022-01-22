package org.iproute.springboot.tcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNetServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

/**
 * Config
 *
 * @author winterfell
 * @since 2022/1/23
 */
@EnableIntegration
@IntegrationComponentScan
@Configuration
public class Config {

//    @Bean
//    @ServiceActivator(inputChannel = "toTcp")
//    public MessageHandler tcpOutGate(AbstractClientConnectionFactory connectionFactory) {
//        TcpOutboundGateway gate = new TcpOutboundGateway();
//        gate.setConnectionFactory(connectionFactory);
//        gate.setOutputChannelName("resultToString");
//        return gate;
//    }

    @Bean
    public TcpInboundGateway tcpInGate(AbstractServerConnectionFactory connectionFactory) {
        TcpInboundGateway inGate = new TcpInboundGateway();
        inGate.setConnectionFactory(connectionFactory);
        inGate.setRequestChannel(fromTcp());
        return inGate;
    }

    @Bean
    public MessageChannel fromTcp() {
        return new DirectChannel();
    }

//    @Bean
//    public AbstractClientConnectionFactory clientCF() {
//        return new TcpNetClientConnectionFactory("localhost", serverCF().getPort());
//    }

    @Bean
    public AbstractServerConnectionFactory serverCF() {
        return new TcpNetServerConnectionFactory(50001);
    }

}
