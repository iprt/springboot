package org.iproute.raft.election.algo.rpcs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 请求投票
 *
 * @author devops@kubectl.net
 * @since 2023-08-27
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class RequestVote {
    // 候选人的任期号
    private long term;

    // 请求选票的候选人ID
    private int candidateId;

    // 候选人最后日志条目的索引值
    private long lastLogIndex;

    // 候选人最后日志条目的任期号
    private long lastLongTerm;
}
