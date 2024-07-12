package org.iproute.mid.camel.boot.netty.dynamichandler.protocol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MyProtocol
 *
 * @author tech@intellij.io
 * @since 2022/8/7
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class Msg {

    private int len;

    private byte[] content;

}
