package org.iproute.mid.logstash.printlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * LogPrinter
 *
 * @author tech@intellij.io
 * @since 2022/7/24
 */
@Component
@Slf4j
public class LogPrinter implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        log.debug("this is and debug log");
        log.info("this is an info log");

        log.error("LogPrinter.run", new RuntimeException("Hello World"));

    }
}
