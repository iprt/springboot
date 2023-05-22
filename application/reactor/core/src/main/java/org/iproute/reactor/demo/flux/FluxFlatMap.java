package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

/**
 * FluxFlatMap
 *
 * @author zhuzhenjie
 * @since 4/24/2023
 */
public class FluxFlatMap {
    public static void main(String[] args) {
        Flux<String> stringFlux1 = Flux.just("a", "b", "c", "d", "e", "f", "g");

        Flux<Flux<String>> stringFlux2 = stringFlux1.window(2);

        stringFlux2.log().subscribe(System.out::println);

        stringFlux2.flatMap(flux1 -> flux1.map(word -> word.toUpperCase()))
                .subscribe(System.out::println);

        Flux<String> stringFlux3 = stringFlux2.flatMap(flux -> flux);
        stringFlux3.subscribe(System.out::println);

    }
}