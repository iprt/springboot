package org.iproute.raft.election.algo;

import org.apache.commons.lang3.RandomUtils;

/**
 * NetMockUtils
 *
 * @author zhuzhenjie
 * @since 2022 /9/27
 */
public class Utils {


    /**
     * Mock net delay. 模拟网路传输的延迟
     */
    static void mockNetDelay() {
        try {
            // 模拟网络延迟
            Thread.sleep(RandomUtils.nextLong(10, 20));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * follower 切换到 candidate 的超时时间
     *
     * @return the long
     */
    static long followerTimeoutSeconds() {
        return RandomUtils.nextLong(2L, 5L);
    }


    /**
     * 选举超时时间
     *
     * @return the long
     */
    static long candidateTimeoutMs() {
        return RandomUtils.nextLong(1000L, 2000L);
    }


    static long heartbeatTimeout() {
        return 500L;
    }

}
