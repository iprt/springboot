package org.ipoute.mid.client.es.test;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Main
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
@Slf4j
public class Log4j2Test {
    // private static final Logger log = LogManager.getLogger(Log4j2Test.class);

    @Test
    public void print() {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

}
