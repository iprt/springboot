package org.iproute.mid.camel.boot.netty.promise;

import io.netty.util.concurrent.DefaultEventExecutor;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Promise;

/**
 * PromiseDemo
 *
 * @author tech@intellij.io
 * @since 2023/2/3
 */
public class PromiseDemo {

    public static void main(String[] args) {

        // 1. 构造线程池
        EventExecutor executor = new DefaultEventExecutor();

        // 2. 创建 DefaultPromise 实例
        Promise<Integer> promise = new DefaultPromise<Integer>(executor);

        // 3.下面给这个 promise 添加两个 listener
        promise
                .addListener(new GenericFutureListener<Future<Integer>>() {
                    @Override
                    public void operationComplete(Future future) throws Exception {
                        if (future.isSuccess()) {
                            System.out.println("任务结束，结果：" + future.get());
                        } else {
                            System.out.println("任务失败，异常：" + future.cause());
                        }
                    }
                }).addListener(new GenericFutureListener<Future<Integer>>() {
                    @Override
                    public void operationComplete(Future future) throws Exception {
                        System.out.println("任务结束 ...");
                    }
                });


        // 提交任务到线程池，五秒后执行结束，设置执行 promise 的结果
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignored) {
                }
                // 设置 promise 的结果
                // promise.setFailure(new RuntimeException());
                promise.setSuccess(123456);
            }
        });


        // main 线程阻塞等待执行结果
        try {
            promise.sync();
        } catch (InterruptedException ignored) {
        }

    }
}
