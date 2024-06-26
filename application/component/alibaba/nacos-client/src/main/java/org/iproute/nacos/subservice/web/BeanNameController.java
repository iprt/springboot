package org.iproute.nacos.subservice.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * BeanNameController
 *
 * @author devops@kubectl.net
 */
@RestController
// @RefreshScope
@Slf4j
public class BeanNameController implements CommandLineRunner {

    @Value("${beanName}")
    private String beanName;

    @GetMapping("/getBeanName")
    public String getBeanName() {
        return beanName;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("bean is blank = {}", StringUtils.isBlank(beanName));
        log.info("bean name is {}", beanName);
    }
}
