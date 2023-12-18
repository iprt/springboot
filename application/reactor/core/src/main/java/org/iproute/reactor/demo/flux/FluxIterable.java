package org.iproute.reactor.demo.flux;

import com.google.common.collect.Lists;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * FluxIterable
 *
 * @author zhuzhenjie
 * @since 2023/2/4
 */
public class FluxIterable {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("hello", "world", "java");

        Flux.fromIterable(list)
                .subscribe(System.out::println);
    }
}
