package org.iproute.springboot.repository.zhuzhenjie;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.springboot.entities.bo.CreateTableSql;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CommonMapper
 *
 * @author winterfell
 * @since 2022/1/23
 */
@Repository
@Mapper
public interface CommonMapper {
    /**
     * 查询数据库中的所有表名
     *
     * @param schema 数据库名
     * @return 表名列表
     */
    List<String> getAllTableNameBySchema(@Param("schema") String schema);

    /**
     * 查询建表语句
     *
     * @param tableName 表名
     * @return 建表语句
     */
    CreateTableSql selectTableCreateSql(@Param("tableName") String tableName);

    /**
     * 执行SQL
     *
     * @param sql 待执行SQL
     */
    void executeSql(@Param("sql") String sql);
}
