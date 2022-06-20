package org.iproute.reactor.demo.mono;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * MonoDemo01
 *
 * @author winterfell
 * @since 2022/4/17
 */
public class MonoDemo01 {

    public static void main(String[] args) {
        Mono<String> noData = Mono.empty();

        Mono<String> data = Mono.just("foo");

        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);

    }
}
