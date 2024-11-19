package org.iproute.middleware.kafka.springboot.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.iproute.middleware.kafka.springboot.component.KafkaMsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * MsgController
 *
 * @author tech@intellij.io
 * @since 2022/3/16
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class MsgController {
    private final KafkaMsgSender kafkaMsgSender;

    @PostMapping("/kafka/send")
    public void sendMsgToKafka(@RequestBody MsgDTO msgDTO) {
        kafkaMsgSender.send(msgDTO.getMsg());
    }

    @ToString
    @Data
    public static class MsgDTO {
        private String msg;
    }
}
