package org.iproute.raft.election.algo;

import org.iproute.raft.election.algo.rpcs.AppendEntries;

import java.util.concurrent.BlockingDeque;

/**
 * 节点为 follower 的时候
 *
 * @author zhuzhenjie
 * @since 2023-08-27
 */
public class Follower {
    private final RaftNode raftNode;

    // 接受消息的 队列
    private BlockingDeque<AppendEntries> receive;


    public Follower(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    public Role start() {
        return Role.candidate;
    }
}