package org.iproute.raft.election.algo;

import lombok.extern.slf4j.Slf4j;

/**
 * raft 节点状态机
 *
 * @author devops@kubectl.net
 * @since 2023-08-27
 */
@Slf4j
public class RaftNodeStateMachine {
    private final RaftNode node;
    private final Follower follower;
    private final Candidate candidate;
    private final Leader leader;

    public RaftNodeStateMachine(RaftNode node) {
        this.node = node;
        this.follower = new Follower(this.node);
        this.candidate = new Candidate(this.node);
        this.leader = new Leader(this.node);
    }

    public void start() {
        log.info("当前节点 {}:{} 开始状态为 follower", node.getNodeId(), node.getNodeName());
        this.doOnFollower();
    }

    private void doOnFollower() {
        Role next = follower.start();
        if (next == Role.candidate) {
            log.info("当前节点 {}:{} follower -> candidate", node.getNodeId(), node.getNodeName());
            this.doOnCandidate();
        } else {
            log.error("当前节点 {}:{} follower -> leader 这是不可能产生的，系统错误", node.getNodeId(), node.getNodeName());
        }
    }

    private void doOnCandidate() {
        Role next = candidate.start();





    }

    private void doOnLeader() {
        Role next = leader.start();
        if (next == Role.follower) {
            this.doOnFollower();
        } else {
            log.error("当前节点 {}:{} leader -> candidate 这是不可能产生的，系统错误", node.getNodeId(), node.getNodeName());
        }
    }

}
