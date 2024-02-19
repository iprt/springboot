package org.iproute.pg.json.service;

import org.iproute.pg.json.entities.Book;

import java.util.List;

/**
 * BookService
 *
 * @author zhuzhenjie
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
}
