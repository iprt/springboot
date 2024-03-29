package org.iproute.pg.json.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.pg.json.entities.Book;
import org.iproute.pg.json.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BookServiceImpl
 *
 * @author zhuzhenjie
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
}
