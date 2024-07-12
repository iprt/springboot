package org.iproute.pg.json.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.pg.json.entities.po.Book;
import org.iproute.pg.json.mapper.BookMapper;
import org.iproute.pg.json.service.BookService;
import org.iproute.pg.json.utils.CastUtils;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * BookServiceImpl
 *
 * @author tech@intellij.io
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;

    @Override
    public int addBook(Book book) {
        return bookMapper.insert(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public int deleteBook(long id) {
        return bookMapper.deleteById(id);
    }

    @Override
    public Page<Book> limitPage(int pageNo, int pageSize) {
        Page<?> page = new Page<>(pageNo, pageSize);
        return bookMapper.limitPage(page);
    }

    @Override
    public List<Map<String, Object>> listReturnMap() {
        List<Map<String, Object>> rows = bookMapper.listReturnMap();

        rows.forEach(row -> {
            PGobject pGobject = CastUtils.cast(row.get("detail"), PGobject.class);
            log.info("book.detail type = {}; value= {}", pGobject.getType(), pGobject.getValue());
        });

        return rows;
    }

}
