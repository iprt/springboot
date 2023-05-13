package org.iproute.mid.camel.boot.netty.eventloop;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;

/**
 * EventLoop
 *
 * @author zhuzhenjie
 * @since 5/7/2023
 */
@Slf4j
public class MyEventLoop extends Thread {

    /**
     * step1
     * <p>
     * container
     */
    private final BlockingQueue<FutureTask<String>> queue;

    public MyEventLoop(BlockingQueue<FutureTask<String>> queue) {
        this.queue = queue;
    }


    /**
     * step2
     */
    @Override
    public void run() {
        // case1 当前线程没有被interrupted掉
        // case2 interrupt 了，但是队列不为空
        while (!Thread.interrupted() || (Thread.interrupted() && !queue.isEmpty())) {
            // consumer
            try {
                FutureTask<String> task = queue.take();
                executeTask(task);
            } catch (InterruptedException e) {
                log.info("MyEventLoop {} interrupted", Thread.currentThread().getName());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * step3
     * <p>
     * Execute task
     *
     * @param task the task
     */
    static void executeTask(final FutureTask<String> task) {
        if (!task.isCancelled()) {
            try {
                task.run();

                // we ran it, but we have to grab the exception if raised
                task.get();
            } catch (Exception e) {
                log.error("MyEventLoop {} reached exception in processing command", Thread.currentThread().getName(), e);
                throw new RuntimeException(e);
            }
        }
    }

}
