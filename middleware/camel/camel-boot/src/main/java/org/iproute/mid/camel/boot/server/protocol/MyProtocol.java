package org.iproute.mid.camel.boot.server.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MyProtocol
 *
 * @author zhuzhenjie
 * @since 2022/8/7
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class MyProtocol {

    private int len;

    private byte[] content;

}
