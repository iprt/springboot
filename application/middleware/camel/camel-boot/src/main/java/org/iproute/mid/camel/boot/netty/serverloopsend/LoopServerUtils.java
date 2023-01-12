package org.iproute.mid.camel.boot.netty.serverloopsend;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * LoopServerUtils
 *
 * @author zhuzhenjie
 * @since 2022/8/21
 */
@Slf4j
public class LoopServerUtils {
    private LoopServerUtils() {

    }

    static final ConcurrentHashMap<String, ChannelDetail> CLIENT_MAP = new ConcurrentHashMap<>();

    private static final ScheduledExecutorService SES = Executors.newSingleThreadScheduledExecutor();

    private static final ExecutorService ES = Executors.newFixedThreadPool(10);

    /**
     * Loop send msg.
     */
    public static void loopSendMsg() {
        // 利用 ScheduledExecutorService 不断发送消息
        AtomicLong i = new AtomicLong(1);
        SES.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("连接的客户端个数 = {}", CLIENT_MAP.size());

                CLIENT_MAP.forEach((channelId, channelDetail) -> {
                    ES.execute(() -> {
                        Channel channel = channelDetail.channel;
                        if (channel.isActive()) {
                            channel.writeAndFlush("Hello World " + i.getAndIncrement());
                        }
                    });
                });
                SES.schedule(this, 1, TimeUnit.SECONDS);
            }
        }, 1, TimeUnit.SECONDS);

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    @Data
    public static class ChannelDetail {
        private String host;
        private int port;
        private Channel channel;
    }
}
