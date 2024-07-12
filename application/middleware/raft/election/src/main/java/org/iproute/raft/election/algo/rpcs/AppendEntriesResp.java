package org.iproute.raft.election.algo.rpcs;

import lombok.Data;

/**
 * AppendEntriesResp
 *
 * @author tech@intellij.io
 * @since 2023-08-27
 */
@Data
public class AppendEntriesResp {
    // 当前任期，对于领导人而言 它会更新自己的任期
    private long term;
    // 如果跟随者所含有的条目和 prevLogIndex 以及 prevLogTerm 匹配上了，则为 true
    private boolean success;
}

/*
接收者的实现：

1. 返回假 如果领导人的任期小于接收者的当前任期 (译者注：这里的接收者是指跟随者或者候选人)
2. 返回假 如果接收者日志中没有包含这样一个条目 即该条目的任期在 prevLogIndex 上能和 prevLogTerm 匹配上
    在接收者日志中 如果能找到一个和 prevLogIndex 以及 prevLogTerm 一样的索引和任期的日志条目 则继续执行下面的步骤 否则返回假
3. 如果一个已经存在的条目和新条目（译者注：即刚刚接收到的日志条目）发生了冲突（因为索引相同，任期不同），那么就删除这个已经存在的条目以及它之后的所有条目

4. 追加日志中尚未存在的任何新条目

5. 如果领导人的已知已提交的最高日志条目的索引大于接收者的已知已提交最高日志条目的索引（leaderCommit > commitIndex），
 则把接收者的已知已经提交的最高的日志条目的索引commitIndex 重置为 领导人的已知已经提交的最高的日志条目的索引 leaderCommit
 或者是 上一个新条目的索引 取两者的最小值

 */
