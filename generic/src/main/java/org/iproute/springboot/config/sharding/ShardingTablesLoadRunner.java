package org.iproute.springboot.config.sharding;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.repository.zhuzhenjie.CommonMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * ShardingTablesLoadRunner
 *
 * @author winterfell
 * @since 2022/1/23
 */
@Slf4j
@Order(1) // 数字越小 越先执行
@Component
public class ShardingTablesLoadRunner implements CommandLineRunner {

    @Value("${db.schema-name}")
    private String schemaName;

    @Resource
    private CommonMapper commonMapper;

    @Override
    public void run(String... args) throws Exception {
        // 给 分表工具类注入属性
        ShardingAlgorithmTool.setCommonMapper(commonMapper);
        // 调用缓存重载方法
        ShardingAlgorithmTool.tableNameCacheReload(schemaName);

        log.info("ShardingTablesLoadRunner start OK");
    }
}
