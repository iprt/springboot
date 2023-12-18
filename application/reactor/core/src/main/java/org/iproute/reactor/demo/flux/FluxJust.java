package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxJust
 *
 * @author zhuzhenjie
 */
public class FluxJust {
    public static void main(String[] args) {
        Flux.just("foo", "bar", "foobar")
                .subscribe(System.out::println);
    }
}
