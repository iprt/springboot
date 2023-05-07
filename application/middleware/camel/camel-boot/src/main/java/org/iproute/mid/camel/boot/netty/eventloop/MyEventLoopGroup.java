package org.iproute.mid.camel.boot.netty.eventloop;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;

/**
 * EventLoopGroup
 *
 * @author zhuzhenjie
 * @since 5/7/2023
 */
@Slf4j
public class MyEventLoopGroup {
    private final int coreNum = Runtime.getRuntime().availableProcessors();

    private final MyEventLoop[] executors;
    // for init MyEventLoop
    private final BlockingQueue<FutureTask<String>>[] queues;
    private final ConcurrentMap<String, Throwable> loopThrownExceptions = new ConcurrentHashMap<>();

    public MyEventLoopGroup(int queenCapacity) {

        executors = new MyEventLoop[coreNum];
        queues = new BlockingQueue[coreNum];

        // init queue
        for (int i = 0; i < coreNum; i++) {
            queues[i] = new ArrayBlockingQueue<>(queenCapacity);
        }

        // init group
        for (int i = 0; i < coreNum; i++) {
            // init event loop
            MyEventLoop eventLoop = new MyEventLoop(queues[i]);

            eventLoop.setName(this.setThreadName(i));

            eventLoop.setUncaughtExceptionHandler((thread, ex) -> {
                // 记录抛出的问题
                loopThrownExceptions.put(thread.getName(), ex);
            });

            // start
            eventLoop.start();

            this.executors[i] = eventLoop;
        }
    }


    // provider
    public String route(String description, Callable<String> cmd) {
        FutureTask<String> task = new FutureTask<>(cmd);

        // 随机选择线程中队列
        int random = RandomUtils.nextInt(0, coreNum);

        // 如果线程相同，就直接执行，不走队列
        if (Thread.currentThread().getName().equals(this.executors[random].getName())) {
            log.info("[{}] same thread, direct call", description);
            try {
                MyEventLoop.executeTask(task);

            } catch (Exception e) {
                log.error("[{}] direct call failed ", description, e);
            }
        }

        if (this.queues[random].offer(task)) {
            log.info("[{}] offered in queue", description);
            return "success";
        } else {
            log.error("[{}] queue {} is full", description, random);
            return "failure";
        }
    }


    public void terminate() {
        for (int i = 0; i < coreNum; i++) {
            this.executors[i].interrupt();
        }

        for (MyEventLoop executor : executors) {
            try {
                executor.join(5_000);
            } catch (InterruptedException e) {
                log.info("Interrupted while joining session event loop {}", executor.getName(), e);
            }
        }

        for (Map.Entry<String, Throwable> loopThrownExceptionEntry : loopThrownExceptions.entrySet()) {
            String threadName = loopThrownExceptionEntry.getKey();
            Throwable threadError = loopThrownExceptionEntry.getValue();
            log.error("Session event loop {} terminated with error", threadName, threadError);
        }
    }


    private String setThreadName(int i) {
        return "Executor " + i;
    }
}
