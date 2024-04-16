package org.iproute.springboot.entities.dto;

import lombok.*;

/**
 * PostDTO
 *
 * @author devops@kubectl.net
 * @since 2021/11/28
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class PostDTO {
    private String name;
}
