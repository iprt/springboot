package org.iproute.reactor.demo.mono;

import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * MonoDefer
 *
 * @author tech@intellij.io
 * @since 4/25/2023
 */
public class MonoDefer {

    public static void main(String[] args) {
        // Mono.just会在声明阶段构造Date对象，只创建一次
        Mono<Date> mono = Mono.just(new Date());

        // 但是Mono.defer却是在subscribe阶段才会创建对应的Date对象
        Mono<Date> defer = Mono.defer(() -> Mono.just(new Date()));

        mono.subscribe(System.out::println);
        defer.subscribe(System.out::println);
        // 延迟5秒钟

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after 5 seconds");
        mono.subscribe(System.out::println);
        defer.subscribe(System.out::println);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("after 8 seconds");

        defer.subscribe(System.out::println);
    }
}
