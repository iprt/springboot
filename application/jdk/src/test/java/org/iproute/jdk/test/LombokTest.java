package org.iproute.jdk.test;

import lombok.extern.slf4j.Slf4j;
import org.iproute.jdk.lombok.DemoBeanExtend;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * LombokTest
 *
 * @author zhuzhenjie
 * @since 2023/8/2
 */
@Slf4j
public class LombokTest {

    @Test
    public void testSuperBuilder() {
        DemoBeanExtend dbe = DemoBeanExtend.builder()
                .id(1L).name("test")
                .random(UUID.randomUUID().toString()).build();

        log.info("DemoBeanExtend is {}", dbe);
    }

}
