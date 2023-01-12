package org.iproute.middleware.seata.at.storage;

import org.iproute.middleware.seata.at.storage.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StorageApplicationTests {
    @Resource
    private StorageService storageService;

    @Test
    public void testStorage1() {
        storageService.decrease("coke", 10);
    }

    @Test
    public void testStorage2() {
        storageService.decrease("coke", 120);
    }

}
