package org.iproute.mid.camel.boot.netty.dynamichandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.camel.boot.netty.dynamichandler.clienthandler.ClientInitializer;
import org.iproute.mid.camel.boot.netty.dynamichandler.protocol.Msg;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * DyhClient
 *
 * @author devops@kubectl.net
 */
@Slf4j
public class DyhClient {

    public static void main(String[] args) {

        Connector connector = new Connector(
                "127.0.0.1", 7002, DyhClient::bootstrapInit
        );

        connector.connect();

        loopSendMsg(connector);
    }

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
            Msg pMsg = Msg.builder()
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
