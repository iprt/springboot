package org.iproute.springboot.config.sharding;

import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * DateShardingAlgorithm
 *
 * @author zhuzhenjie
 * @since 2022/1/23
 */
@Slf4j
public class DateShardingAlgorithm extends ShardingAlgorithmTool<Date> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> preciseShardingValue) {
        return shardingTablesCheckAndCreateAndReturn(preciseShardingValue.getLogicTableName(),
                preciseShardingValue.getLogicTableName() + "_" + ShardingUtils.getSuffixByYearMonth(preciseShardingValue.getValue()));
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<Date> rangeShardingValue) {
        log.info("availableTargetNames : {}", availableTargetNames);
        log.info("rangeShardingValue : {}", rangeShardingValue);
        Range<Date> valueRange = rangeShardingValue.getValueRange();

        Date lowerDate = valueRange.lowerEndpoint();
        Date upperDate = valueRange.upperEndpoint();

        // 2023_1
        String lowerSuffix = ShardingUtils.getSuffixByYearMonth(lowerDate);

        // 2023_12
        String upperSuffix = ShardingUtils.getSuffixByYearMonth(upperDate);

        List<String> suffixList = ShardingUtils.getSuffixListForRange(lowerSuffix, upperSuffix);

        Set<String> suffixListSet = Sets.newHashSet(suffixList);

        List<String> rangedTables = new ArrayList<>();
        // availableTargetNames : request_log_2022_1 ... request_log_2025_12
        for (String tableName : availableTargetNames) {
            if (containTableName(suffixListSet, tableName)) {
                rangedTables.add(tableName);
            }
        }

        log.info("match tableNames----------------------- {}", rangedTables);
        // compare cache and create table
        String logicTableName = rangeShardingValue.getLogicTableName();

        for (String rangedTable : rangedTables) {
            // 如果表不存在，则创建
            super.shardingTablesCheckAndCreateAndReturn(logicTableName, rangedTable);
        }

        return rangedTables;
    }

    private boolean containTableName(Set<String> suffixListSet, String tableName) {
        boolean flag = false;
        for (String s : suffixListSet) {
            if (tableName.endsWith(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
