package org.iproute.middleware.kafka.springboot.entities;

import lombok.*;

import java.util.Date;

/**
 * Message
 *
 * @author zhuzhenjie
 * @since 2022/3/16
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Message {
    private Long id;
    private String msg;
    private Date sendTime;
}
