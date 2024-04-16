package org.iproute.springboot.repository.mysql;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.mysql.MysqlUser;
import org.springframework.stereotype.Repository;

/**
 * MysqlUserMapper
 *
 * @author devops@kubectl.net
 * @since 2022/7/19
 */
@Repository
@Mapper
@DS("mysql")
public interface MysqlUserMapper extends BaseMapper<MysqlUser> {
}