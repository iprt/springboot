package org.iproute.pg.json.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.iproute.pg.json.entities.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * BookMapper
 *
 * @author zhuzhenjie
 */
@Repository
@Mapper
public interface BookMapper extends BaseMapper<Book> {

    /**
     * Limits the results of a given page by filtering out any records that do not match the specified criteria.
     *
     * @param page The page containing the records to be limited
     * @return A new page with the limited results
     */
    Page<Book> limitPage(Page<?> page);


    /**
     * Retrieves a list of mappings, each representing a record in the database.
     * Each mapping has a String key representing the column name, and an Object value representing the column value.
     *
     * @return a list of mappings representing the records
     */
    List<Map<String, Object>> listReturnMap();

}
