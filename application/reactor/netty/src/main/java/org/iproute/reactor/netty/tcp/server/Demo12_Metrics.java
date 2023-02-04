package org.iproute.reactor.netty.tcp.server;

import reactor.netty.DisposableServer;
import reactor.netty.channel.ChannelMetricsRecorder;
import reactor.netty.tcp.TcpServer;

import java.net.SocketAddress;
import java.time.Duration;

/**
 * Demo12_Metrics
 *
 * @author winterfell
 * @since 2023/2/5
 */
public class Demo12_Metrics {

    public static void main(String[] args) {
        DisposableServer server =
                TcpServer.create()
                        .metrics(true, CustomChannelMetricsRecorder::new) //<1>
                        .bindNow();

        server.onDispose()
                .block();
    }

    private static class CustomChannelMetricsRecorder implements ChannelMetricsRecorder {
        @Override
        public void recordDataReceived(SocketAddress socketAddress, long l) {
        }

        @Override
        public void recordDataSent(SocketAddress socketAddress, long l) {
        }

        @Override
        public void incrementErrorsCount(SocketAddress socketAddress) {
        }

        @Override
        public void recordTlsHandshakeTime(SocketAddress socketAddress, Duration duration, String s) {
        }

        @Override
        public void recordConnectTime(SocketAddress socketAddress, Duration duration, String s) {
        }

        @Override
        public void recordResolveAddressTime(SocketAddress socketAddress, Duration duration, String s) {
        }
    }
}
