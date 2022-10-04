package org.iproute.raft.election.algo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟计算机的节点
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
@Slf4j
public class Node implements Runnable {

    @Setter
    @Getter
    private String name;

    // 当前的角色
    private Role role;
    private final ReentrantLock roleLock = new ReentrantLock();

    public Role getRole() {
        roleLock.lock();
        try {
            return role;
        } finally {
            roleLock.unlock();
        }
    }

    public void setRole(Role role) {
        roleLock.lock();
        try {
            this.role = role;
        } finally {
            roleLock.unlock();
        }
    }

    @Setter
    @Getter
    // 集群信息
    private List<Node> clusterInfo;

    @Setter
    @Getter
    private volatile boolean isLeader;

    @Setter
    @Getter
    // 共识的信息
    volatile String leaderName;

    // 当前的任期信息
    private long iterm;

    private final ReentrantLock itermLock = new ReentrantLock();

    public long getIterm() {
        itermLock.lock();
        try {
            return iterm;
        } finally {
            itermLock.unlock();
        }
    }

    public void setIterm(long iterm) {
        itermLock.lock();
        try {
            this.iterm = iterm;
        } finally {
            itermLock.unlock();
        }
    }

    public Node(String name) {
        this.name = name;
        this.isLeader = false;
        this.leaderName = null;
        this.iterm = 0L;
    }

    @Override
    public void run() {
        try {
            // 状态迁移
            new NodeStateMachine(this).run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private final ReentrantLock hbLock = new ReentrantLock();
    // 心跳
    private HeartBeat heartBeat;

    public HeartBeat getHeartBeat() {
        hbLock.lock();
        try {
            return heartBeat;
        } finally {
            hbLock.unlock();
            // 很重要
            heartBeat = null;
        }
    }

    // 节点接收到心跳，等价于 其他节点发送心跳
    public void receiveHeartBeat(String leaderName, Long iterm) {
        log.info("当前节点{},{},接收到来自Leader {} 的心跳，任期为: {}", this.getRole(), this.getName(), leaderName, iterm);
        hbLock.lock();
        try {
            // 接收到心跳，一定是来自leader的心跳
            this.setRole(Role.Follower);
            // 重置任期
            this.setIterm(iterm);
            this.setLeader(false);
            this.setLeaderName(leaderName);
            if (heartBeat == null) {
                this.heartBeat = new HeartBeat(leaderName, iterm);
            } else {
                this.heartBeat.setIterm(iterm);
            }
        } finally {
            hbLock.unlock();
        }
    }

    private final ReentrantLock voteReqLock = new ReentrantLock();

    // 两阶段的优化思路，两次 RPC 合并成一次 RPC
    // 接收到候选者的投票信息
    public VoteRsp receiveVoteReq(VoteReq voteReq) {
        // 查看当前任期
        // log.info("当前节点{}的任期为 {},{}投票的任期为 {}", this.getName(), iterm, voteReq.getTempLeader(), voteReq.getIterm());
        voteReqLock.lock();
        try {
            Utils.mockNetDelay();
            // 当前节点如果是候选人状态, leaderName 默认都会设置成自己
            if (this.role == Role.Candidate) {
                if (voteReq.getIterm() == this.getIterm()) {
                    log.info("当前节点{}为候选者，收到相同任期的候选者{}的投票，说明两个人同时变成候选者并选举|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName, false);
                } else if (voteReq.getIterm() > this.getIterm()) {
                    // 这种情况一般发生在两个候选者一轮选举没选举出来，重新选举
                    log.info("当前节点{}为候选者，收到更大任期的候选者{}的投票，|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    // 角色切换
                    this.setRole(Role.Follower);
                    return new VoteRsp(voteReq.getIterm(), voteReq.getCandidateLeader(), false);
                } else {
                    log.info("当前节点{}为候选者，收到更小任期的候选者{}的投票，|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName, false);
                }
            } else if (this.role == Role.Follower) {
                if (voteReq.getIterm() == this.getIterm()) {
                    log.info("当前节点{}为跟随者，收到相同任期的候选者{}的投票 |当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName, false);
                } else if (voteReq.getIterm() > this.getIterm()) {
                    log.info("当前节点{}为跟随者，收到更大任期的候选者{}的投票，无条件认为候选者{}可以当选|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(voteReq.getIterm(), voteReq.getCandidateLeader(), false);
                } else {
                    log.info("当前节点{}为跟随者，收到更小任期的候选者{}的投票，认为候选者{}不能当选|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName, false);
                }
            } else {
                if (voteReq.getIterm() == this.getIterm()) {
                    log.info("当前节点{}为领导者，收到相同任期的候选者{}的投票，通知候选者自己是Leader|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName, true);
                } else if (voteReq.getIterm() > this.getIterm()) {
                    log.info("当前节点{}为领导者，收到更大任期的候选者{}的投票，自己要降级|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    this.setRole(Role.Follower);
                    return new VoteRsp(voteReq.getIterm(), voteReq.getCandidateLeader(), false);
                } else {
                    log.info("当前节点{}为领导者，收到更小任期的候选者{}的投票，自己要降级|当前节点信息name={},iterm={}|投票信息 leaderName={},iterm={}",
                            this.getName(), voteReq.getCandidateLeader(), this.getName(), this.getIterm(), voteReq.getCandidateLeader(), voteReq.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName, true);
                }
            }

        } finally {
            voteReqLock.unlock();
        }
    }

}
