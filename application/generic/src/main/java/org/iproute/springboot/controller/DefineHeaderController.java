package org.iproute.springboot.controller;

import org.iproute.springboot.config.aop.RecordParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DefineHeaderController
 *
 * @author tech@intellij.io
 * @since 2022/7/19
 */
@RestController
@RecordParameters
public class DefineHeaderController {

    /**
     * 自定义 header
     *
     * @return the string
     */
    @GetMapping(value = "/defineHeader")
    public String header() {
        return "hello world";
    }

}
