package org.iproute.springboot.entities.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * LocalTimeReq
 *
 * @author tech@intellij.io
 * @since 2022/1/23
 */
@Data
public class LocalTimeReq {
    private LocalDateTime time;
}
