package org.iproute.raft.election;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * HeartBeat
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class HeartBeat {
    private String leader;
    private long iterm;
}
