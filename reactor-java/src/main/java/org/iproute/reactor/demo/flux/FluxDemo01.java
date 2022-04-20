package org.iproute.reactor.demo.flux;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * FluxDemo01
 *
 * @author winterfell
 * @since 2022/4/17
 */
public class FluxDemo01 {

    public static void main(String[] args) {
        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");

        List<String> strs = Arrays.asList("foo", "bar", "foobar");
        Flux<String> seq2 = Flux.fromIterable(strs);

    }
}
