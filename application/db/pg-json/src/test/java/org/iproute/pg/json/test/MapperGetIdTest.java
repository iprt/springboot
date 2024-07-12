package org.iproute.pg.json.test;

import lombok.RequiredArgsConstructor;
import org.iproute.pg.json.entities.po.DemoBeanLeaf;
import org.iproute.pg.json.mapper.DemoBeanLeafMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

/**
 * MapperGetIdTest
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class MapperGetIdTest {
    private final DemoBeanLeafMapper demoBeanLeafMapper;

    @Test
    public void testGetSnowflakeId() {
        DemoBeanLeaf leafBean = DemoBeanLeaf.builder()
                .name(UUID.randomUUID().toString())
                .build();

        System.err.println("before insert|" + leafBean);

        int insert = demoBeanLeafMapper.insert(leafBean);

        if (insert > 0) {
            System.err.println("after insert|" + leafBean);
        }

    }
}
