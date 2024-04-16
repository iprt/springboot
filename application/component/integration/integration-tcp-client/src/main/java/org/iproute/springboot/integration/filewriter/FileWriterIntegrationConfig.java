package org.iproute.springboot.integration.filewriter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.File;

/**
 * FileWriterIntegrationConfig
 *
 * @author devops@kubectl.net
 * @since 2023/2/6
 */
@Configuration
public class FileWriterIntegrationConfig {
    public static final String xmlConfig = "xmlConfig";
    public static final String javaConfig = "javaConfig";
    public static final String dslConfig = "dslConfig";

    private static final String pathname = "D:/tmp";


    @Profile(xmlConfig)
    @Configuration
    @ImportResource("classpath:/filewriter-config.xml")
    public static class XmlConfiguration {
    }

    @Profile(javaConfig)
    @Bean
    @Transformer(inputChannel = "textInChannel",
            outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> uppercaseTransformer() {
        return String::toUpperCase;
    }


    @Profile(javaConfig)
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(
                new File(pathname)
        );

        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);
        return handler;
    }

    //
    // DSL Configuration
    //
    @Profile(dslConfig)
    @Bean
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows
                .from(MessageChannels.direct("textInChannel"))
                .<String, String>transform(String::toUpperCase)
                .handle(
                        Files.outboundAdapter(new File(pathname))
                                .fileExistsMode(FileExistsMode.APPEND)
                                .appendNewLine(true))
                .get();
    }
}
