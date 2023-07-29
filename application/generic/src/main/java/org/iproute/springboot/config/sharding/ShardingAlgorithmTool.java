package org.iproute.springboot.config.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.iproute.springboot.entities.bo.CreateTableSql;
import org.iproute.springboot.repository.commons.CommonMapper;

import java.util.HashSet;
import java.util.List;

/**
 * 分表工具
 */
@Slf4j
public abstract class ShardingAlgorithmTool<T extends Comparable<?>> implements PreciseShardingAlgorithm<T>, RangeShardingAlgorithm<T> {

    private static CommonMapper commonMapper;

    public static final HashSet<String> TABLE_NAME_CACHE = new HashSet<>();

    /**
     * 手动注入
     */
    public static void setCommonMapper(CommonMapper commonMapper) {
        ShardingAlgorithmTool.commonMapper = commonMapper;
    }

    /**
     * 判断 分表获取的表名是否存在 不存在则自动建表
     *
     * @param logicTableName  逻辑表名(表头)
     * @param resultTableName 真实表名
     * @return 确认存在于数据库中的真实表名
     */
    public String shardingTablesCheckAndCreateAndReturn(String logicTableName, String resultTableName) {

        log.info("分库分表的类加载器为 : {}", this.getClass().getClassLoader());

        synchronized (logicTableName.intern()) {
            // 缓存中有此表 返回
            if (shardingTablesExistsCheck(resultTableName)) {
                return resultTableName;
            }

            // 缓存中无此表 建表 并添加缓存
            CreateTableSql createTableSql = commonMapper.selectTableCreateSql(logicTableName);
            String sql = createTableSql.getCreateTable();
            sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
            sql = sql.replace(logicTableName, resultTableName);
            commonMapper.executeSql(sql);
            TABLE_NAME_CACHE.add(resultTableName);
        }

        return resultTableName;
    }

    /**
     * 判断表是否存在于缓存中
     *
     * @param resultTableName 表名
     * @return 是否存在于缓存中
     */
    public boolean shardingTablesExistsCheck(String resultTableName) {
        return TABLE_NAME_CACHE.contains(resultTableName);
    }

    /**
     * 缓存重载方法
     *
     * @param schemaName 待加载表名所属数据库名
     */
    public static void tableNameCacheReload(String schemaName) {

        // 读取数据库中所有表名
        List<String> tableNameList = commonMapper.getAllTableNameBySchema(schemaName);
        // 删除旧的缓存(如果存在)
        ShardingAlgorithmTool.TABLE_NAME_CACHE.clear();
        // 写入新的缓存
        ShardingAlgorithmTool.TABLE_NAME_CACHE.addAll(tableNameList);
    }

}
