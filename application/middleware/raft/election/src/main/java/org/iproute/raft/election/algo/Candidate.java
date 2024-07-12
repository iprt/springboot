package org.iproute.raft.election.algo;

/**
 * 节点为候选人
 *
 * @author tech@intellij.io
 * @since 2023-08-27
 */
public class Candidate {
    private final RaftNode raftNode;

    public Candidate(RaftNode raftNode) {
        this.raftNode = raftNode;
    }

    public Role start() {
        return Role.follower;
    }
}
