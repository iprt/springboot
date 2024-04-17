package org.iproute.pg.json.service.impl;

import org.iproute.pg.json.service.SayService;
import org.springframework.stereotype.Service;

/**
 * HiSayServiceImpl
 *
 * @author devops@kubectl.net
 */
@Service("hiSayService")
public class HiSayServiceImpl implements SayService {
    @Override
    public String say() {
        return "Hi";
    }
}
