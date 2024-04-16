package org.iproute.middleware.zk.lock.service;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.iproute.middleware.zk.lock.config.ZkServerConfig;
import org.springframework.stereotype.Service;

/**
 * WarehouseService
 *
 * @author devops@kubectl.net
 * @since 2022/3/17
 */
@Service
public class WarehouseService {

    public static int shoe = 10;

    private final ZkServerConfig zkServerConfig;

    public WarehouseService(ZkServerConfig zkServerConfig) {
        this.zkServerConfig = zkServerConfig;
    }

    /**
     * Out of warehouse int.
     *
     * @return the int
     * @throws Exception the exception
     */
    public int outOfWarehouse() throws Exception {
        if (WarehouseService.shoe > 0) {
            // 模拟业务
            Thread.sleep(1000);
            return --WarehouseService.shoe;
        } else {
            throw new RuntimeException("库存不足");
        }
    }

    /**
     * Out of warehouse with lock int.
     *
     * @return the int
     * @throws Exception the exception
     */
    public int outOfWarehouseWithLock() throws Exception {
        // 设置zk客户端重试策略，每隔5秒重试一次，最多重试10次
        RetryPolicy policy = new ExponentialBackoffRetry(5000, 10);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkServerConfig.getConnectString())
                .retryPolicy(policy)
                .build();
        // 建立socket连接
        client.start();

        final InterProcessMutex mutex = new InterProcessMutex(client, "/lock/shoe");

        try {
            // 获取锁
            mutex.acquire();

            if (WarehouseService.shoe > 0) {
                // 模拟业务
                Thread.sleep(1000);
                return --WarehouseService.shoe;
            } else {
                throw new RuntimeException("库存不足");
            }

        } finally {
            // 释放锁
            mutex.release();
        }
    }
}
