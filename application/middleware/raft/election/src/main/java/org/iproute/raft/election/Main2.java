package org.iproute.raft.election;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Main2
 *
 * @author zhuzhenjie
 * @since 2022/9/22
 */
public class Main2 {

    private static boolean flag = true;

    public static void main(String[] args) {

        System.out.println(getFlag());

        System.out.println(flag);

        ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();

        ses.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world");
                ses.schedule(this, 1, TimeUnit.SECONDS);
            }
        }, 3, TimeUnit.SECONDS);

        CountDownLatch ctl = new CountDownLatch(1);

        new Thread(() -> {
            try {
                Thread.sleep(200);
                System.out.println("CountDownLatch hello world");
                ctl.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        try {
            System.out.println("before await");
            boolean await = ctl.await(1, TimeUnit.SECONDS);
            System.out.println("await = " + await);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean getFlag() {
        try {
            return flag;
        } finally {
            flag = false;
        }
    }

}
