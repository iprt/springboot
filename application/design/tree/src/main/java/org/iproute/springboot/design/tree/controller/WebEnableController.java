package org.iproute.springboot.design.tree.controller;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.design.tree.config.WebEnableService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * WebEnableController
 *
 * @author zhuzhenjie
 * @since 2022/12/2
 */
@RestController
@Slf4j
public class WebEnableController {

    /**
     * Say hello string.
     *
     * @return the string
     */
    @GetMapping("/sayHello")
    public String sayHello() {
        return "Hello,World";
    }

    @GetMapping(value = WebEnableService.ENABLE_URI)
    public boolean enable() {
        log.info("enable to provide service");
        WebEnableService.enable();
        return true;
    }

    @GetMapping(value = WebEnableService.DISABLE_URI)
    public boolean disable() {
        log.info("disable to provide service");
        WebEnableService.disable();
        return false;
    }

}
