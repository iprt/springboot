package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxRange
 *
 * @author tech@intellij.io
 */
public class FluxRange {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .subscribe(
                        System.out::println,
                        System.err::println,
                        () -> System.out.println("finished"));
    }
}
