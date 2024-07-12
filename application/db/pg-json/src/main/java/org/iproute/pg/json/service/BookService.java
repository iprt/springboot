package org.iproute.pg.json.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.iproute.pg.json.entities.po.Book;

import java.util.List;
import java.util.Map;

/**
 * BookService
 *
 * @author tech@intellij.io
 */
public interface BookService {


    /**
     * Adds a book to the library.
     *
     * @param book the book to be added
     * @return the ID of the added book
     */
    int addBook(Book book);

    /**
     * Retrieves a list of all books in the library.
     *
     * @return a list of all books
     */
    List<Book> getAllBooks();


    /**
     * Deletes a book from the library based on the specified ID.
     *
     * @param id the ID of the book to be deleted
     * @return the number of books deleted (0 or 1)
     */
    int deleteBook(long id);

    /**
     * Retrieves a limited page of books from the library.
     *
     * @param pageNo    the page number to retrieve
     * @param pageSize  the number of books per page
     * @return a {@link Page} object containing the limited page of books
     */
    Page<Book> limitPage(int pageNo, int pageSize);

    /**
     * Retrieves a list of mappings, each representing a record in the database.
     * Each mapping has a String key representing the column name, and an Object value representing the column value.
     *
     * @return a list of mappings representing the records
     */
    List<Map<String, Object>> listReturnMap();
}
