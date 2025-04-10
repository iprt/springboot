package org.iproute.mid.es.boot;

import lombok.RequiredArgsConstructor;
import org.iproute.mid.es.boot.document.RecordLog;
import org.iproute.mid.es.boot.service.RecordLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

/**
 * EsDocumentTest
 *
 * @author tech@intellij.io
 * @since 2025-04-10
 */
@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EsDocumentTest {
    private final RecordLogService recordLogService;

    @Test
    public void testSave() {
        recordLogService.save(RecordLog.builder()
                .id(UUID.randomUUID().toString()).date(new Date()).productId(1L)
                .content("test")
                .build());
    }

}
