package org.iproute.raft.election;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * VoteReq
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class VoteReq {
    private long iterm;

    // 候选leader
    private String candidateLeader;
}
