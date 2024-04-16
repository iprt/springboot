package org.iproute.springboot.design.tdengine.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.springboot.design.tdengine.entity.Demo;
import org.springframework.stereotype.Repository;

/**
 * DemoMapper
 *
 * @author devops@kubectl.net
 * @since 2022/5/30
 */
@Mapper
@Repository
public interface DemoMapper extends BaseMapper<Demo> {

    /**
     * Drop table int.
     *
     * @return the int
     */
    int dropTable();

    /**
     * Create table int.
     *
     * @return the int
     */
    int createTable();

    /**
     * insert row
     *
     * @param nickname the nickname
     * @return the int
     */
    int insert(@Param("nickname") String nickname);
}
