package org.iproute.springboot.repository.springboot;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.springframework.stereotype.Repository;

/**
 * RequestLogMapper 需要指定sharding的数据源
 *
 * @author zhuzhenjie
 * @since 2021/11/25
 */
@Repository
@Mapper
@DS("springboot")
public interface RequestLogBeanMapper extends BaseMapper<RequestLogBean> {
}