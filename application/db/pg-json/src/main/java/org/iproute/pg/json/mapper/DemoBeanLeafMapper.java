package org.iproute.pg.json.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.pg.json.entities.po.DemoBeanLeaf;
import org.springframework.stereotype.Repository;

/**
 * DemoBeanLeafMapper
 *
 * @author tech@intellij.io
 */
@Repository
@Mapper
public interface DemoBeanLeafMapper extends BaseMapper<DemoBeanLeaf> {
}