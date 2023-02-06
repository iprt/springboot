package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxNever
 *
 * @author zhuzhenjie
 * @since 2023/2/4
 */
public class FluxNever {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .timeout(Flux.never(), v -> Flux.never())
                .subscribe(System.out::println);
    }
}
