package org.iproute.springboot.integration.filewriter;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * FileWriterGateway
 *
 * @author devops@kubectl.net
 * @since 2023/2/6
 */
@MessagingGateway(
        defaultRequestChannel = "textInChannel"
)
public interface FileWriterGateway {

    /**
     * Write to file.
     *
     * @param filename the filename
     * @param data     the data
     */
    void writeToFile(
            @Header(FileHeaders.FILENAME) String filename,
            String data);

}
