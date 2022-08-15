package org.iproute.mid.camel.boot.server.reconnect;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Timer;
import java.util.TimerTask;


/**
 * NettyConnection
 *
 * 参考地址  <a href="https://gist.github.com/ochinchina/e97606fd0b15f106c91c">...</a>
 *
 * @author zhuzhenjie
 * @since 2022/8/15
 */
@Slf4j
public class NettyConnection {
    private Bootstrap bootstrap = new Bootstrap();
    private final SocketAddress addr_;
    private Channel channel_;
    private final Timer timer_;

    public NettyConnection(String host, int port, Timer timer) {
        this(new InetSocketAddress(host, port), timer);
    }

    public NettyConnection(SocketAddress addr, Timer timer) {
        this.addr_ = addr;
        this.timer_ = timer;
        bootstrap.group(new NioEventLoopGroup());
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(createNMMessageHandler());
            }
        });

        scheduleConnect(10);
    }

    public void send(String msg) throws IOException {
        if (channel_ != null && channel_.isActive()) {
            ByteBuf buf = channel_.alloc().buffer().writeBytes(msg.getBytes());
            channel_.writeAndFlush(buf);
        } else {
            throw new IOException("Can't send message to inactive connection");
        }
    }

    public void close() {
        try {
            channel_.close().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void doConnect() {
        try {
            ChannelFuture f = bootstrap.connect(addr_);
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {// if is not successful, reconnect
                        future.channel().close();
                        bootstrap.connect(addr_).addListener(this);
                    } else {// good, the connection is ok
                        channel_ = future.channel();
                        // add a listener to detect the connection lost
                        addCloseDetectListener(channel_);
                        connectionEstablished();

                    }
                }

                private void addCloseDetectListener(Channel channel) {
                    // if the channel connection is lost, the ChannelFutureListener.operationComplete() will be called
                    channel.closeFuture().addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future)
                                throws Exception {
                            connectionLost();
                            scheduleConnect(5);
                        }

                    });

                }
            });
        } catch (Exception ex) {
            scheduleConnect(1000);

        }
    }

    private void scheduleConnect(long millis) {
        timer_.schedule(new TimerTask() {
            @Override
            public void run() {
                doConnect();
            }
        }, millis);
    }

    private ChannelHandler createNMMessageHandler() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) {
                ByteBuf buf = (ByteBuf) msg;
                int n = buf.readableBytes();
                if (n > 0) {
                    byte[] b = new byte[n];
                    buf.readBytes(b);
                    handleMessage(new String(b));
                }
            }

        };
    }


    public void handleMessage(String msg) {
        System.out.println(msg);

    }

    public void connectionLost() {
        System.out.println("connectionLost()");
    }

    public void connectionEstablished() {
        try {
            send("hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String... args) {
        NettyConnection conn = new NettyConnection("127.0.0.1", 34567, new Timer());

        for (; ; ) {
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
            }
        }
    }


}
