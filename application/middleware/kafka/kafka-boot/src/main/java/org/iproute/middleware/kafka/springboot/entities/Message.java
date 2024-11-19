package org.iproute.middleware.kafka.springboot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Message
 *
 * @author tech@intellij.io
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
