package org.iproute.raft.election.algo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * VoteReq
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class VoteRsp {
    private long iterm;

    // 谁想当Leader
    private String possibleLeader;


    private boolean fromLeader;
    // 存在的leader
    // private String existedLeader;
}
