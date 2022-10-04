package org.iproute.raft.election.algo;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 节点状态机
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
@Slf4j
public class NodeStateMachine {

    private final ScheduledExecutorService FOLLOWER_SES;

    private final ExecutorService EXCHANGE_ES;

    private final ScheduledExecutorService LEADER_SES;


    // 存储节点的信息
    private final Node node;

    public NodeStateMachine(Node node) {
        this.node = node;
        // 跟随者
        FOLLOWER_SES = Executors.newSingleThreadScheduledExecutor();

        EXCHANGE_ES = Executors.newFixedThreadPool(node.getClusterInfo().size() + 1);

        // 发送心跳的
        LEADER_SES = Executors.newScheduledThreadPool(node.getClusterInfo().size());
    }

    public void run() {
        // 一开始，所有的节点都是 follower 状态
        node.setRole(Role.Follower);
        this.runAsRole();
    }


    private void runAsRole() {
        if (node.getRole() == Role.Follower) {
            EXCHANGE_ES.execute(this::runAsFollower);

        } else if (node.getRole() == Role.Candidate) {
            EXCHANGE_ES.execute(this::runAsCandidate);

        } else if (node.getRole() == Role.Leader) {
            EXCHANGE_ES.execute(this::runAsLeader);
        }
    }

    private void runAsFollower() {
        // 重要：随机定时,小于心跳的
        // long rs = Utils.followerTimeoutSeconds();
        long rs = 2L;

        log.info("节点 {} 开始以 {} 的状态运行, 随机定时器时间为 {}", node.getName(), node.getRole(), rs);

        // 切换到 follower 的时候，把节点自身的标记重置
        this.node.setLeaderName(null);
        this.node.setLeader(false);


        FOLLOWER_SES.schedule(new Runnable() {
            @Override
            public void run() {
                // 在 randomSeconds()时间到了的时候，还没有收到leader的心跳,
                HeartBeat heartBeat = node.getHeartBeat();
                if (heartBeat == null) {
                    log.info("Follower节点 {} 在randomSeconds() = {}s时间到了的时候，还没有收到leader的心跳", node.getName(), rs);
                    // 角色切换 ，也就是状态切换 ，从 Follower 变成 Candidate
                    node.setRole(Role.Candidate);
                    runAsRole();
                    log.info("节点 {} 从Follower状态切换到Candidate状态", node.getName());
                } else {
                    // 接收到 Leader的同步数据 在 raft 中，等价于接收到了节点的心跳，目前这次只做选举
                    log.info("Follower节点 {} 发现 Leader {} 的心跳，当前任期为 {} ,重置随机定时器 ,当前随机定时器时间为 {}", node.getName(), heartBeat.getLeaderName(), node.getIterm(), rs);
                    FOLLOWER_SES.schedule(this, rs, TimeUnit.SECONDS);
                }
            }
        }, rs, TimeUnit.SECONDS);

    }


    // 不考虑网络分区的情况
    private void runAsCandidate() {
        log.info("节点 {} 开始以 {} 的状态运行，想选举自己为Leader", node.getName(), node.getRole());

        // Candidate 只是一种临时状态
        while (node.getRole() == Role.Candidate) {
            // 为什么想到双重检查，因为节点的状态随时会改变
            log.info("候选者 {} 第一次双重检查角色", node.getName());
            // 判断是否获取到了心跳
            HeartBeat maybeContains = this.node.getHeartBeat();
            // 当candidate节点等待其他节点时，如果收到了来自其它节点的AppendEntries RPC请求，同时做个请求中带上的任期号不比candidate节点的小，那么说明集群中已经存在leader了
            if (maybeContains != null && maybeContains.getIterm() >= this.node.getIterm()) {
                this.node.setIterm(maybeContains.getIterm());
                this.node.leaderName = maybeContains.getLeaderName();
                this.node.setRole(Role.Follower);
                break;
            }

            // 投票通过的个数
            AtomicInteger votesPassed = new AtomicInteger(0);

            // 向集群中所有节点发送，投票，先投票自己 候选者 = （提议者 + 接收者）
            log.info("候选者 {} 把自己的任期 +1 ,新的任期为 {}, 一开始假设自己就是 Leader", node.getName(), node.getIterm() + 1);
            // 候选者把自己的任期+1 ，自己投票 +1
            votesPassed.incrementAndGet();
            node.setIterm(node.getIterm() + 1);

            // TODO 技巧？： 把准备阶段和提交阶段在一次RPC中完成，节点就认为自己是Leader，不过成不成功不知道，不成功就再选一遍，加上延迟
            // TODO 不成功的话，虽然任期+1了，但是可能退化成 Follower
            node.setLeaderName(this.node.getName());

            // 投票请求
            VoteReq voteReq = new VoteReq(node.getIterm(), node.getName());

            log.info("候选者 {},向其他人开始推荐自己", node.getName());

            // 是否选举的时候发现了Leader
            AtomicBoolean findLeader = new AtomicBoolean(false);

            // 发现更大的任期
            AtomicInteger findBiggerIterm = new AtomicInteger(0);

            // 半数以上成功同意就算成功
            int mid = node.getClusterInfo().size() / 2 + 1;

            CountDownLatch ctd = new CountDownLatch(node.getClusterInfo().size() - 1);
            // 3. 模拟发送消息，
            for (Node otherNode : node.getClusterInfo()) {
                // 自己不用再投票了
                if (!otherNode.getName().equals(node.getName())) {
                    // 真实环境是异步的
                    EXCHANGE_ES.execute(new Runnable() {
                        // 是否需要重新选举
                        @Override
                        public void run() {
                            // 模拟 投票 返回结果
                            VoteRsp otherVoteRsp = otherNode.receiveVoteReq(voteReq);
                            if (otherVoteRsp.getIterm() == voteReq.getIterm()
                                    && voteReq.getCandidateLeader().equals(otherVoteRsp.getPossibleLeader())
                                    && !otherVoteRsp.isFromLeader()) {
                                // 任期相同，得到的 leader 也相同
                                votesPassed.incrementAndGet();
                            } else if (voteReq.getIterm() <= otherVoteRsp.getIterm()
                                    && otherVoteRsp.isFromLeader()) {
                                // 小于等于当前投票节点的任期内存在Leader
                                findLeader.compareAndSet(false, true);
                            } else if (otherVoteRsp.getIterm() > voteReq.getIterm()) {
                                // 投票发现返回的任期 比 自己本身的任期还要大，需要降级
                                findBiggerIterm.incrementAndGet();
                            }
                            ctd.countDown();
                        }
                    });
                }
            }

            log.info("候选者 {},向其他人推荐完自己，等待结果", node.getName());

            try {
                // 模拟 选举超时
                boolean await = ctd.await(Utils.candidateTimeoutMs(), TimeUnit.MILLISECONDS);

                if (!await) {
                    log.info("候选者 {} 在节点超时的情况下，双重检查角色", node.getName());
                    if (node.getRole() != Role.Candidate) {
                        log.info("候选人 {} 节点超时的情况下，发现角色已经变更 ,新的角色为 {}", node.getName(), node.getRole());
                        break;
                    }

                    if (findLeader.get()) {
                        node.setRole(Role.Follower);
                        break;
                    }

                    if (findBiggerIterm.get() > 0) {
                        node.setRole(Role.Follower);
                        break;
                    }
                    maybeContains = this.node.getHeartBeat();
                    if (maybeContains != null && maybeContains.getIterm() >= this.node.getIterm()) {
                        this.node.setIterm(maybeContains.getIterm());
                        this.node.leaderName = maybeContains.getLeaderName();
                        this.node.setRole(Role.Follower);
                        break;
                    }

                    if (votesPassed.get() >= mid) {
                        // 判断是否已经有leader的心跳了
                        HeartBeat heartBeat = node.getHeartBeat();
                        if (heartBeat != null) {
                            node.setRole(Role.Follower);
                            break;
                        }
                        // 集群中超过半数认同当前节点作为 Leader
                        node.setRole(Role.Leader);
                        break;
                    }
                    // 选举超时了，但是没有选举出Leader，需要重新选举
                    continue;
                }

            } catch (InterruptedException e) {
                // TODO ...
            }

            log.info("候选者 {} 在节点不超时的情况下，双重检查角色", node.getName());
            // 还是候选者状态的时候，收到了Leader的心跳，说明自己肯定不能成为Leader了
            if (node.getRole() != Role.Candidate) {
                log.info("候选人 {} 节点不超时的情况下，发现角色已经变更 ,新的角色为 {}", node.getName(), node.getRole());
                break;
            }

            if (findLeader.get()) {
                node.setRole(Role.Follower);
                break;
            }

            if (findBiggerIterm.get() > 0) {
                log.info("候选者 {} 不超时的情况下，但是存发现了更大的任期，把自己降级为跟随者，需要重新选举", node.getName());
                // 当前节点降级
                node.setRole(Role.Follower);
                break;
            }

            maybeContains = this.node.getHeartBeat();
            if (maybeContains != null && maybeContains.getIterm() >= this.node.getIterm()) {
                this.node.setIterm(maybeContains.getIterm());
                this.node.leaderName = maybeContains.getLeaderName();
                this.node.setRole(Role.Follower);
                break;
            }

            // 节点获得半数以上的投票
            if (votesPassed.get() >= mid) {

                // 变成leader之前判断是否已经有leader生成了
                HeartBeat heartBeat = node.getHeartBeat();
                if (heartBeat != null) {
                    node.setRole(Role.Follower);
                    break;
                }

                // 半数以上投票成功了
                log.info("候选者 {} 获得半数以上投票 成为 Leader,当前任期为 {}", node.getName(), node.getIterm());
                node.setRole(Role.Leader);
                break;
            }

            log.info("候选者 {} 选举失败了，重新选举", node.getName());
        }

        runAsRole();
    }

    private void runAsLeader() {
        log.info("节点 {} 开始以 {} 的状态运行 ,并立即向子节点发送心跳", node.getName(), node.getRole());
        node.setLeader(true);

        for (Node other : node.getClusterInfo()) {
            if (!other.isLeader()) {
                LEADER_SES.schedule(new Runnable() {
                    @Override
                    public void run() {
                        // 每次发送之前都要判断下自己有没有主动降级了
                        if (!node.isLeader()) {
                            // 角色切换
                            runAsRole();
                            return;
                        }
                        // 模拟网络延迟，可能会造成重新选举
                        Utils.mockNetDelay();
                        other.receiveHeartBeat(node.getName(), node.getIterm());

                        LEADER_SES.schedule(this, Utils.heartbeatTimeout(), TimeUnit.MILLISECONDS);
                    }
                }, 0, TimeUnit.MILLISECONDS);
            }
        }
    }

}
