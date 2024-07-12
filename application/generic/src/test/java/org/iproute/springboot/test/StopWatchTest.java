package org.iproute.springboot.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * StopWatchTest
 *
 * @author tech@intellij.io
 * @since 2023/7/30
 */
@Slf4j
public class StopWatchTest {

    @Test
    public void testCost() throws InterruptedException {

        StopWatch stopWatch = new StopWatch();

        stopWatch.start();

        TimeUnit.SECONDS.sleep(1);

        stopWatch.stop();

        log.info(stopWatch.prettyPrint());
    }

}
