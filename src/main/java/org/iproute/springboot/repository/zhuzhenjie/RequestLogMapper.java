package org.iproute.springboot.repository.zhuzhenjie;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.RequestLog;
import org.springframework.stereotype.Repository;

/**
 * RequestLogMapper
 *
 * @author winterfell
 * @since 2021/11/25
 */
@Repository
@Mapper
public interface RequestLogMapper extends BaseMapper<RequestLog> {
}