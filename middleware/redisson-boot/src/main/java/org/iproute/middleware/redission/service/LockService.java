package org.iproute.middleware.redission.service;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LockService
 *
 * @author winterfell
 * @since 2022/3/18
 */
@Service
@Slf4j
public class LockService {

    public static int count = 10;

    @Autowired
    private RedissonClient redissonClient;

    public String reset() {
        count = 10;
        return "reset";
    }

    /**
     * Decrease without lock
     *
     * @return the int
     * @throws Exception the exception
     */
    public int decrease() throws Exception {
        if (count > 0) {
            Thread.sleep(1000);
            return --count;
        } else {
            throw new RuntimeException("count can't little than zero");
        }
    }

    /**
     * Decrease with lock
     *
     * @return the int
     */
    public int decreaseWithLock() throws Exception {
        RLock lock = redissonClient.getLock("lock:decrease");
        lock.lock();

        // lock.lock(10, TimeUnit.SECONDS);

        try {
            if (count > 0) {
                Thread.sleep(1000);
                return --count;
            } else {
                throw new RuntimeException("count can't little than zero");
            }
        } finally {
            lock.unlock();
        }
    }

}
