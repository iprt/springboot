package org.iproute.reactor.demo.mono;

import reactor.core.publisher.Mono;

/**
 * MonoThen
 *
 * @author tech@intellij.io
 * @since 4/25/2023
 */
public class MonoThen {
    public static void main(String[] args) {
        Str str = new Str("hello");
        Mono.just(str)
                .then(Mono.just(str.append(" world")))
                .subscribe(System.out::println);

        Mono.just("hello")
                .then(Mono.just("world"))
                .subscribe(System.out::println);
    }


    static class Str {
        String s;

        public Str(String s) {
            this.s = s;
        }

        public Str append(String ap) {
            this.s = this.s + ap;
            return this;
        }


        @Override
        public String toString() {
            return "Str{" +
                    "s='" + s + '\'' +
                    '}';
        }
    }
}
