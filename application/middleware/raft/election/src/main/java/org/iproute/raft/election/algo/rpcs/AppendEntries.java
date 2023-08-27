package org.iproute.raft.election.algo.rpcs;

import lombok.Data;
import org.iproute.raft.election.algo.LogRow;

import java.util.List;

/**
 * AppendEntries 追加条目
 * <p>
 * 由领导人调用，用于日志条目的复制，同时也被当做心跳使用
 *
 * @author zhuzhenjie
 * @since 2023-08-27
 */
@Data
public class AppendEntries {
    // 领导人的任期
    private long term;

    // 领导人 ID 因此跟随者可以对客户端进行重定向（译者注：跟随者根据领导人 ID 把客户端的请求重定向到领导人，比如有时客户端把请求发给了跟随者而不是领导人）
    private long leaderId;

    // 紧邻新日志条目之前的那个日志条目的索引
    private long prevLogIndex;

    // 紧邻新日志条目之前的那个日志条目的任期
    private long prevLogTerm;

    // 需要被保存的日志条目（被当做心跳使用时，则日志条目内容为空；为了提高效率可能一次性发送多个）
    // entries[]
    private List<LogRow> entries;

    // 领导人的已知已提交的最高的日志条目的索引
    private long leaderCommit;

}
