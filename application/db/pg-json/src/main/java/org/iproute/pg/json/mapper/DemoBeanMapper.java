package org.iproute.pg.json.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.pg.json.entities.po.DemoBean;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * DemoMapper
 *
 * @author tech@intellij.io
 */
@Repository
@Mapper
public interface DemoBeanMapper extends BaseMapper<DemoBean> {

    List<DemoBean> getDemoBeanList(@Param("startTime") Date startTime,
                                   @Param("endTime") Date endTime);

}
