package org.iproute.springboot.repository.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.User;
import org.springframework.stereotype.Repository;

/**
 * UserMapper
 *
 * @author winterfell
 * @since 2021/11/25
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
