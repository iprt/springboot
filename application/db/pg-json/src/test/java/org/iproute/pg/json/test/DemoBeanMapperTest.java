package org.iproute.pg.json.test;

import lombok.RequiredArgsConstructor;
import org.iproute.pg.json.entities.po.DemoBean;
import org.iproute.pg.json.mapper.DemoBeanMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

/**
 * DemoBeanMapperTest
 *
 * @author devops@kubectl.net
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SpringBootTest
public class DemoBeanMapperTest {
    private final DemoBeanMapper demoBeanMapper;

    @Test
    public void testJavaUtilDate() {
        Date now = new Date();
        List<DemoBean> demoBeanList = demoBeanMapper.getDemoBeanList(
                new Date(now.getTime() - 365 * 24 * 60 * 60 * 1000L),
                new Date(now.getTime() + 365 * 24 * 60 * 60 * 1000L)
        );
        demoBeanList.forEach(System.err::println);
    }

}
