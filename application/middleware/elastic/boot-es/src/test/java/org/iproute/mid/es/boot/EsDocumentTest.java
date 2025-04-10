package org.iproute.mid.es.boot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.iproute.mid.es.boot.document.RecordLog;
import org.iproute.mid.es.boot.service.RecordLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;
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
    private final ObjectMapper objectMapper;

    @Test
    public void testSave() {
        recordLogService.save(RecordLog.builder()
                .id(UUID.randomUUID().toString()).date(new Date()).productId(1L)
                .content("test")
                .build());
    }


    @Test
    public void testFindById() {
        Optional<RecordLog> recordLog = recordLogService.findById("802f4047-7d4d-4d36-99eb-ca114d3a0651");
        recordLog.ifPresent(r -> {
            try {
                System.out.println(objectMapper.writeValueAsString(r));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
