package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxError
 *
 * @author winterfell
 * @since 2023/2/4
 */
public class FluxError {
    public static void main(String[] args) {
        Flux.error(new RuntimeException("Flux Error"))
                .subscribe(
                        System.out::println,
                        e -> System.err.println(e.getLocalizedMessage())
                );
    }
}
