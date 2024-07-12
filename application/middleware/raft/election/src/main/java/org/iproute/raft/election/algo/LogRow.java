package org.iproute.raft.election.algo;

import lombok.Data;

/**
 * 日志条目
 *
 * @author tech@intellij.io
 * @since 2023-08-27
 */
@Data
public class LogRow {
    private String key;
    private int value;
}
