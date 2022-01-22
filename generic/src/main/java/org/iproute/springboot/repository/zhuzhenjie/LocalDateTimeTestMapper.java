package org.iproute.springboot.repository.zhuzhenjie;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.LocalDateTimeTestBean;
import org.springframework.stereotype.Repository;

/**
 * LocalTimeTestMapper
 *
 * @author winterfell
 * @since 2022/1/23
 */
@Repository
@Mapper
public interface LocalDateTimeTestMapper extends BaseMapper<LocalDateTimeTestBean> {
}
