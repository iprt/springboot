package org.iproute.middleware.seata.at.points;

import org.iproute.middleware.seata.at.points.service.PointsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PointsApplicationTests {
    @Resource
    private PointsService pointsService;

    @Test
    public void testPoints(){
        pointsService.increase("zhangsan", 10);
    }
}
