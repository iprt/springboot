package org.ipoute.mid.client.es.test;

import lombok.extern.slf4j.Slf4j;
import org.iproute.mid.client.es.config.EsConfig;
import org.junit.jupiter.api.Test;

/**
 * ConfigTest
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
public class ConfigTest {

    @Test
    public void show() {
        EsConfig esConfig = EsConfig.get();

    }
}
