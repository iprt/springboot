package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxEmpty
 *
 * @author devops@kubectl.net
 */
public class FluxEmpty {

    public static void main(String[] args) {
        Flux.empty()
                .switchIfEmpty(s -> System.out.println("is empty " + s))
                .subscribe();
    }
}
