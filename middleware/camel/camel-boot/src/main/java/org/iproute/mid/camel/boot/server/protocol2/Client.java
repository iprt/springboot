package org.iproute.mid.camel.boot.server.protocol2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.server.protocol2.clienthandler.ClientInitializer;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

/**
 * client
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class Client {

    @SuppressWarnings("all")
    public static void main(String[] args) {

        Connector connector = new Connector(
                "127.0.0.1", 7002, Client::bootstrapInit
        );

        connector.connect();

        loopSendMsg(connector);
    }

    @SuppressWarnings("all")
    private static void loopSendMsg(Connector connector) {
        AtomicLong id = new AtomicLong(1);

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
        ses.schedule(new Runnable() {
            @Override
            public void run() {
                boolean success = send(connector, "Hello World " + id.getAndIncrement());
                if (!success) {
                    id.decrementAndGet();
                }
                ses.schedule(this, 1, TimeUnit.SECONDS);
            }
        }, 1, TimeUnit.SECONDS);

    }


    static void bootstrapInit(Bootstrap bootstrap) {
        NioEventLoopGroup worker = new NioEventLoopGroup(2);
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ClientInitializer());
    }


    /**
     * 自定义协议发送消息
     *
     * @param connector the connector
     * @param msg       the msg
     */
    static boolean send(Connector connector, String msg) {
        Channel channel = connector.getChannel();
        if (channel != null && channel.isActive()) {
            SimpleProtocol pMsg = SimpleProtocol.builder()
                    .len(msg.getBytes().length)
                    .content(msg.getBytes())
                    .build();

            channel.writeAndFlush(pMsg);
            return true;
        } else {
            // throw new IOException("Can't send message to inactive connection");
            log.error("Can't send message to inactive connection");
            return false;
        }
    }

}
