package org.iproute.mid.camel.boot.netty.reconnect;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * TimerTest
 *
 * @author tech@intellij.io
 * @since 2022/8/19
 */
@SuppressWarnings("all")
public class TimerTest {

    public static void main(String[] args) {

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello world");

                timer.cancel();
            }
        }, 100);


        // 循环打印
        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        ses.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("ScheduledExecutorService: hello world");
                ses.schedule(this, 1, TimeUnit.SECONDS);
            }
        }, 1, TimeUnit.SECONDS);

    }
}
