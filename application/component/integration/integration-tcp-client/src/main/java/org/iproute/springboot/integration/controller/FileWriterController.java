package org.iproute.springboot.integration.controller;

import lombok.Data;
import org.iproute.springboot.integration.filewriter.FileWriterGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * FileWriterController
 *
 * @author devops@kubectl.net
 * @since 2023/2/6
 */
@RestController
public class FileWriterController {

    @Resource
    private FileWriterGateway fileWriterGateway;

    @PostMapping("/file/write")
    public String writeToFile(@RequestBody FileWriterReq req) {
        fileWriterGateway.writeToFile(
                req.getFilename(),
                req.getText()
        );
        return "ok";
    }

    @Data
    public static class FileWriterReq {
        private String filename;
        private String text;
    }

}
