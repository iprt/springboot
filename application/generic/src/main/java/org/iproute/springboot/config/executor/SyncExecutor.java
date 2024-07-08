package org.iproute.springboot.config.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * SyncExecutor
 *
 * @author devops@kubectl.net
 * @since 2022/1/23
 */
@Configuration
public class SyncExecutor {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = 5;

    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 10;

    /**
     * 阻塞队列容量
     */
    private static final int QUEUE_CAPACITY = 20;

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);

        // executor.setThreadNamePrefix(THREAD_NAME_PREFIX);

        // 设置，当任务满额时将新任务(如果有的话)，打回到原线程去执行。
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        executor.setBeanName("Async");

        executor.initialize();
        return executor;
    }

}
