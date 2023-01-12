package org.iproute.springboot.repository.commons;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.Dept;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DeptMapper
 *
 * @author winterfell
 * @since 2021/11/27
 */
@Repository
@Mapper
@DS("commons")
public interface DeptMapper extends BaseMapper<Dept> {

    /**
     * Children list.
     *
     * @param id the id
     * @return the list
     */
    List<Dept> children(long id);

}
