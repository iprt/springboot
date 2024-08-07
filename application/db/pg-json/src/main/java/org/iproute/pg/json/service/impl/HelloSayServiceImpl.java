package org.iproute.pg.json.service.impl;

import org.iproute.pg.json.service.SayService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * HelloSay
 *
 * @author tech@intellij.io
 */
@Service("helloSayService")
@Primary
public class HelloSayServiceImpl implements SayService {

    @Override
    public String say() {
        return "Hello";
    }
}
