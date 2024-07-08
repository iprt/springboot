package org.iproute.springboot.repository.springboot;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.springboot.entities.po.RequestLogBean;
import org.springframework.stereotype.Repository;

/**
 * RequestLogMapper 需要指定sharding的数据源
 *
 * @author devops@kubectl.net
 * @since 2021/11/25
 */
@Repository
@Mapper
public interface RequestLogBeanMapper extends BaseMapper<RequestLogBean> {
}