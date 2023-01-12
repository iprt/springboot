package org.iproute.mid.client.es;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Main
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
public class Log4j2Test {
    private static final Logger log = LogManager.getLogger(Log4j2Test.class);

    public static void main(String[] args) {
        log.debug("debug");
        log.info("info");
        log.warn("warn");
        log.error("error");
    }

}
