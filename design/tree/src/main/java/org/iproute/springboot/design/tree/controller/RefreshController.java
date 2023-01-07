package org.iproute.springboot.design.tree.controller;

import org.iproute.springboot.design.tree.model.refresh.RefreshBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefeshController
 *
 * @author zhuzhenjie
 * @since 2022/12/3
 */
@RestController
public class RefreshController {

    @Autowired
    private RefreshBean refreshBean;

    @GetMapping("/refreshBean")
    public RefreshBean refreshBean() {
        return refreshBean;
    }

}
