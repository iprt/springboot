package org.iproute.springboot.entities.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * LocalTimeReq
 *
 * @author devops@kubectl.net
 * @since 2022/1/23
 */
@Data
public class LocalTimeReq {
    private LocalDateTime time;
}
