package org.iproute.mid.camel.boot.netty.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * RemoteInfo
 *
 * @author tech@intellij.io
 * @since 2022/8/20
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class RemoteInfo {
    private String host;
    private Integer port;
}
