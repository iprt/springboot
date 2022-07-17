package org.ipoute.mid.client.es.test;

import org.iproute.mid.client.es.config.EsConfig;
import org.junit.Test;

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
        System.out.println(esConfig.toString());
    }
}
