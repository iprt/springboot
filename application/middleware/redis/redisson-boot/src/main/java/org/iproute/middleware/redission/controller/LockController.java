package org.iproute.middleware.redission.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.middleware.redission.service.LockService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LockController
 *
 * @author tech@intellij.io
 * @since 2022 /3/18
 */
@RequiredArgsConstructor
@RestController
@Slf4j
public class LockController {

    private final LockService lockService;

    /**
     * 测试 RLock
     *
     * @return the string
     */
    @GetMapping("/rLock")
    public String rLock() {
        try {
            /*
            int i = lockService.decrease();
            */
            int i = lockService.decreaseWithLock();
            log.info("Thread Name is = {}, Lock Service count = {}", Thread.currentThread().getName(), i);
            return "success";
        } catch (Exception e) {
            log.error("Thread Name is = {}, Exception info is = {}", Thread.currentThread().getName(), e.getMessage());
            return "failure";
        }
    }

    @GetMapping("/reset")
    public String reset() {
        log.info("reset lock service count");
        return lockService.reset();
    }


}
