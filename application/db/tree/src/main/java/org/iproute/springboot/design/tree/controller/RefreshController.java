package org.iproute.springboot.design.tree.controller;

import lombok.AllArgsConstructor;
import org.iproute.springboot.design.tree.model.refresh.RefreshBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RefreshController
 *
 * @author devops@kubectl.net
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
