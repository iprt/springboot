package org.iproute.pg.json.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.pg.json.entities.po.User;
import org.springframework.stereotype.Repository;

/**
 * UserMapper
 *
 * @author tech@intellij.io
 * @since 2025-01-16
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
