package org.iproute.springboot.design.tree.controller;

import lombok.AllArgsConstructor;
import org.iproute.springboot.design.tree.model.refresh.RefreshBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefeshController
 *
 * @author zhuzhenjie
 * @since 2022/12/3
 */
@AllArgsConstructor
@RestController
public class RefreshController {

    private final RefreshBean refreshBean;

    @GetMapping("/refreshBean")
    public RefreshBean refreshBean() {
        return refreshBean;
    }

}
