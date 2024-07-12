package org.iproute.pg.json.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.pg.json.bo.DynamicQueryParam;
import org.iproute.pg.json.bo.DynamicTypeRow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DynamicMapper
 *
 * @author tech@intellij.io
 */
@Repository
@Mapper
public interface DynamicMapper {

    List<DynamicTypeRow> dynamicSelect(@Param("dynamicParam") DynamicQueryParam dynamicParam);

}
