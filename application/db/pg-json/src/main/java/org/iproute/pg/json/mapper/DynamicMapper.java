package org.iproute.pg.json.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.pg.json.bo.GroupQueryParam;
import org.iproute.pg.json.bo.GroupRow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DynamicMapper
 *
 * @author devops@kubectl.net
 */
@Repository
@Mapper
public interface DynamicMapper {

    List<GroupRow> dynamicSelect(@Param("groupParam") GroupQueryParam dynamicParam);

}
