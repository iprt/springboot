package org.iproute.middleware.kafka.springboot.controller;

import org.iproute.middleware.kafka.springboot.component.MyKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * MsgController
 *
 * @author tech@intellij.io
 * @since 2022/3/16
 */
@RestController
public class MsgController {

    @Autowired
    private MyKafkaProducer myKafkaProducer;


    /**
     * Send msg to kafka string.
     *
     * @param msg the msg
     * @return the string
     */
    @GetMapping("/sendMsgToKafka/{msg}")
    public String sendMsgToKafka(@PathVariable("msg") String msg) {
        myKafkaProducer.send(msg);
        return msg;
    }

}
