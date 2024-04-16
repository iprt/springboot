package org.iproute.raft.election.algo.rpcs;

import lombok.Data;

/**
 * 请求投票
 *
 * @author devops@kubectl.net
 * @since 2023-08-27
 */
@Data
public class RequestVoteResp {
    // 当前任期号，以便于候选人去更新自己的任期号
    private long term;

    // 候选人赢得了此张选票时为真
    private boolean voteGranted;
}

/*
接收者实现:
1. 如果term < currentTerm返回 false

2. 如果 votedFor 为空或者为 candidateId，并且候选人的日志至少和自己一样新，那么就投票给他
 */
