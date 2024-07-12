package org.iproute.springboot.design.tree.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * WenEnableService
 *
 * @author tech@intellij.io
 * @since 2022/12/2
 */
@Slf4j
public class WebEnableService {
    public static final String ENABLE_URI = "/service/enable";
    public static final String DISABLE_URI = "/service/disable";
    private static boolean ProvideService = false;
    private static final Lock LOCK = new ReentrantLock(true);


    public static boolean canProvideService() {
        LOCK.lock();
        try {
            return ProvideService;
        } catch (Exception e) {
            return false;
        } finally {
            LOCK.unlock();
        }
    }

    /**
     * 不对外提供服务
     */
    public static void disable() {
        LOCK.lock();
        try {
            ProvideService = false;
        } catch (Exception e) {
            log.error("", e);
        } finally {
            LOCK.unlock();
        }
    }


    /**
     * 对外提供服务
     */
    public static void enable() {
        LOCK.lock();
        try {
            ProvideService = true;
        } catch (Exception e) {
            log.error("", e);
        } finally {
            LOCK.unlock();
        }
    }
}
