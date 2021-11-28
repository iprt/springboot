package org.iproute.springboot.repository.mysql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.Dept;
import org.springframework.stereotype.Repository;

/**
 * DeptMapper
 *
 * @author winterfell
 * @since 2021/11/27
 */
@Repository
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {

}
