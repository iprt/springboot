package org.iproute.pg.json.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.pg.json.entities.Book;
import org.springframework.stereotype.Repository;

/**
 * BookMapper
 *
 * @author zhuzhenjie
 */
@Repository
@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
