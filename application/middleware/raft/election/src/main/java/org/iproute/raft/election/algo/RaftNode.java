package org.iproute.raft.election.algo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * raft 节点
 *
 * @author tech@intellij.io
 * @since 2023-08-26
 */
@Data
public class RaftNode {

    // 节点ID,名称
    private int nodeId;
    private int nodeName;

    // 当前的角色
    private Role role;
    private List<Integer> otherNode;

    /*  -------- */


    /**
     * 所有服务器上的持久性状态
     */
    // 当前任期
    private long currentTerm;
    // 当前任期内收到选票的 candidateId，如果没有投给任何候选人 则为空
    private int votedFor;
    // 日志条目；每个条目包含了用于状态机的命令，以及领导人接收到该条目时的任期（初始索引为1）
    // log[]
    private List<LogRow> log;

    /**
     * 所有服务器上的易失性状态
     */
    // 已知已提交的最高的日志条目的索引（初始值为0，单调递增）
    private long commitIndex;
    // 已经被应用到状态机的最高的日志条目的索引（初始值为0，单调递增
    private long lastApplied;

    /*
        所有服务器需遵守的规则：
        1. 如果commitIndex > lastApplied，则 lastApplied 递增，并将log[lastApplied]应用到状态机中
        2. 如果接收到的 RPC 请求或响应中，任期号T > currentTerm，则令 currentTerm = T，并切换为跟随者状态
     */

    /*
     * 领导人（服务器）上的易失性状态 (选举后已经重新初始化)
     */
    // nextIndex[] 对于每一台服务器，发送到该服务器的下一个日志条目的索引（初始值为领导人最后的日志条目的索引+1）
    // matchIndex[] 对于每一台服务器，已知的已经复制到该服务器的最高日志条目的索引（初始值为0，单调递增）
    private Map<Integer, Long> nextIndex;
    private Map<Integer, Long> matchIndex;

}