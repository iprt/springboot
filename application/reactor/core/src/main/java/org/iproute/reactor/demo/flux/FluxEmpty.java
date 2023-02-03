package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxEmpty
 *
 * @author winterfell
 * @since 2023/2/4
 */
public class FluxEmpty {

    public static void main(String[] args) {
        Flux.empty()
                .switchIfEmpty(s -> System.out.println("is empty"))
                .subscribe();
    }
}
