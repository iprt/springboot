package org.iproute.springboot.config.sharding;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
        List<String> list = new ArrayList<>();
        log.info("availableTargetNames : " + availableTargetNames);
        log.info(rangeShardingValue.toString());
        Range<Date> valueRange = rangeShardingValue.getValueRange();
        Date lowerDate = valueRange.lowerEndpoint();
        Date upperDate = valueRange.upperEndpoint();
        String lowerSuffix = ShardingUtils.getSuffixByYearMonth(lowerDate);
        String upperSuffix = ShardingUtils.getSuffixByYearMonth(upperDate);
        TreeSet<String> suffixList = ShardingUtils.getSuffixListForRange(lowerSuffix, upperSuffix);
        for (String tableName : availableTargetNames) {
            if (containTableName(suffixList, tableName)) {
                list.add(tableName);
            }
        }
        log.info("match tableNames----------------------- {}", list);
        return list;
    }

    private boolean containTableName(Set<String> suffixList, String tableName) {
        boolean flag = false;
        for (String s : suffixList) {
            if (tableName.endsWith(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
