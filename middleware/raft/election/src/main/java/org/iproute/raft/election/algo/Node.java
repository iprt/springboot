package org.iproute.raft.election.algo;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
            // 当前节点是候选人状态
            if (this.getRole() == Role.Candidate) {
                if (voteReq.getIterm() == this.getIterm()) {
                    log.info("当前节点 {} 是候选人,接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),任期相同，说明有了多个候选者，{}先成为候选者，{}后成为候选者并把自己的任期+1了",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm(), this.getName(), voteReq.getCandidateLeader());
                    if (this.leaderName != null) {
                        // todo 每个节点在一个任期中，只能给一个节点投票
                        return new VoteRsp(this.getIterm(), this.leaderName);
                    }
                    return new VoteRsp(this.getIterm(), this.leaderName);
                } else if (voteReq.getIterm() > this.iterm) {
                    log.info("当前节点 {} 是候选人,接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),接收的任期 > 自己的任期 ,同意投票人{}当选Leader, 并且自己降级为Follower",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm(), voteReq.getCandidateLeader());
                    // 主动降级！！！ 节点在候选状态结束后会降级成 follower，等待Leader的心跳
                    this.setRole(Role.Follower);

                    // 修改任期 和 同意的 leader
                    this.setIterm(voteReq.getIterm());
                    this.leaderName = voteReq.getCandidateLeader();

                    return new VoteRsp(this.getIterm(), this.leaderName);
                } else {
                    log.info("当前节点 {} 是候选人,接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),接收的任期 < 自己的任期 ,否定投票人{}当选Leader, 并且希望投票人主动降级为Follower", this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm(), voteReq.getCandidateLeader());
                    return new VoteRsp(this.getIterm(), this.leaderName);
                }

            } else if (this.getRole() == Role.Follower) {

                if (voteReq.getIterm() == this.getIterm()) {
                    log.info("当前节点 {} 是跟随者,接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),因为任期相同，自己是跟随者的身份，所以同意候选人{}当选",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm(), voteReq.getCandidateLeader());

                    if (this.leaderName != null) {
                        // todo 每个节点在一个任期中，只能给一个节点投票
                        return new VoteRsp(this.getIterm(), this.leaderName);
                    }
                    this.leaderName = voteReq.getCandidateLeader();

                    return new VoteRsp(this.getIterm(), this.leaderName);
                } else if (voteReq.getIterm() > this.iterm) {
                    log.info("当前节点 {} 是跟随者,接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),候选人的任期 > 自己的任期 无条件接收候选人{}当选",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm(), voteReq.getCandidateLeader());
                    // 修改任期 和 候选的leader 并返回出去
                    this.setIterm(voteReq.getIterm());
                    this.leaderName = voteReq.getCandidateLeader();
                    return new VoteRsp(this.getIterm(), this.leaderName);
                } else {
                    log.info("当前节点 {} 是跟随者,接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),候选人的任期 < 自己的任期 不能接收候选人{}当选，并且希望候选人自己降级为跟随者",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm(), voteReq.getCandidateLeader());
                    return new VoteRsp(this.getIterm(), this.leaderName);
                }

            } else {
                // 当前节点是领导者，只接受任期更高的请求
                if (voteReq.getIterm() == this.getIterm()) {
                    log.info("当前节点 {} 是领导者，接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),要求候选人自己降级到跟随者",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName);
                } else if (voteReq.getIterm() > this.getIterm()) {
                    log.info("当前节点 {} 是领导者，接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),候选人的任期比自己还要大,说明领导者可能发生了什么故障,要主动降级"
                            , this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm());

                    // 主动降级
                    this.setRole(Role.Follower);
                    this.setLeader(false);

                    // 更新任期和leaderName
                    this.setIterm(voteReq.getIterm());
                    this.leaderName = voteReq.getCandidateLeader();

                    return new VoteRsp(this.getIterm(), this.leaderName);
                } else {
                    log.info("当前节点 {} 是领导者，接收到另外一个候选人{}的投票 | 接收的数据 (投票人={},任期={}), 自己的数据(节点名称={},任期={}),要求候选人自己降级到跟随者",
                            this.getName(), voteReq.getCandidateLeader(), voteReq.getCandidateLeader(), voteReq.getIterm(), this.getName(), this.getIterm());
                    return new VoteRsp(this.getIterm(), this.leaderName);
                }
            }

        } finally {
            voteReqLock.unlock();
        }
    }

}
