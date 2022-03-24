package org.iproute.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebApplication
 *
 * @author winterfell
 * @since 2022/3/24
 */
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

    @RestController
    public static class HelloController {
        @GetMapping("/")
        public String get() {
            return "hello world";
        }
    }
}
