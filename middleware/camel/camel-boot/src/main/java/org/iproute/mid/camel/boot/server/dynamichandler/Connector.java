package org.iproute.mid.camel.boot.server.dynamichandler;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Client
 *
 * @author zhuzhenjie
 * @since 2022/8/19
 */
@Slf4j
public class Connector {

    private final Bootstrap bootstrap = new Bootstrap();

    private final SocketAddress serverAddr;

    /**
     * 连接到server的channel
     */
    @Getter
    private Channel channel;

    private final ScheduledExecutorService ses;


    @SuppressWarnings("all")
    public Connector(String host, int port, Consumer<Bootstrap> bootstrapInit) {
        this(host, port,
                Executors.newSingleThreadScheduledExecutor(), bootstrapInit);
    }

    public Connector(String host, int port, ScheduledExecutorService ses, Consumer<Bootstrap> bootstrapInit) {
        this(new InetSocketAddress(host, port), ses, bootstrapInit);
    }

    public Connector(SocketAddress serverAddr, ScheduledExecutorService ses, Consumer<Bootstrap> bootstrapInit) {
        this.serverAddr = serverAddr;
        this.ses = ses;

        bootstrapInit.accept(bootstrap);
    }


    public void connect() {
        this.doConnect();
    }

    public void connect(long msDelay) {
        this.connect(msDelay, TimeUnit.MILLISECONDS);
    }

    public void connect(long delay, TimeUnit unit) {
        ses.schedule(this::doConnect, delay, unit);
    }

    private void doConnect() {
        try {
            ChannelFuture f = bootstrap.connect(serverAddr);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        connectionLost();

                        bootstrap.connect(serverAddr).addListener(this);
                    } else {
                        channel = future.channel();
                        this.addCloseDetectListener(channel);
                        log.info("connection established");
                    }
                }

                private void addCloseDetectListener(Channel channel) {

                    // if the channel connection is lost, the ChannelFutureListener.operationComplete() will be called
                    channel.closeFuture().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            connectionLost();

                            connect(10);
                        }
                    });
                }
            });

        } catch (Exception e) {
            log.error("doConnect failed", e);
        }

    }

    private void connectionLost() {
        log.error("connection lost");
    }

}
