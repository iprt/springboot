package org.iproute.mid.camel.boot.netty.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MyMsg
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class MyMsg {
    private boolean success;
    private String data;
}
