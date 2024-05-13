package org.iproute.pg.json.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.pg.json.entities.po.DemoBean;
import org.springframework.stereotype.Repository;

/**
 * DemoMapper
 *
 * @author devops@kubectl.net
 */
@Repository
@Mapper
public interface DemoBeanMapper extends BaseMapper<DemoBean> {
}
