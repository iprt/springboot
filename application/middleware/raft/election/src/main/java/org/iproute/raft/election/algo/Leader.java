package org.iproute.raft.election.algo;

/**
 * 当前节点为follower的时候
 *
 * @author zhuzhenjie
 * @since 2023-08-27
 */
public class Leader {
    private final RaftNode raftNode;

    public Leader(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    public Role start() {
        return Role.follower;
    }
}
