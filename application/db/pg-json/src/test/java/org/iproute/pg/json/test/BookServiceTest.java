package org.iproute.pg.json.test;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Lists;
import org.iproute.pg.json.entities.Book;
import org.iproute.pg.json.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * BookServiceTest
 *
 * @author zhuzhenjie
 */
@SpringBootTest
public class BookServiceTest {

    @Resource
    private BookService bookService;

    final long id = 123L;

    @Test
    public void testInsert() {
        Date now = new Date();
        int i = bookService.addBook(
                Book.builder()
                        .id(id)
                        .detail(
                                Book.Detail.builder()
                                        .title("hello pg json")
                                        .remark("postgresql json")
                                        .createTime(now)
                                        .updateTime(DateUtils.addDays(now, 1))
                                        .build()
                        )
                        .authors(Lists.newArrayList(
                                Book.Author.builder().authorName("zhuzhenjie").age(18).build(),
                                Book.Author.builder().authorName("John Smith").age(20).build()
                        ))
                        .build()
        );

        Assertions.assertEquals(1, i);
    }

    @Test
    public void testBatchInsert() {
        IntStream.range(0, 1000).forEach(i -> {
            Date now = new Date();
            bookService.addBook(
                    Book.builder()
                            .id(i)
                            .detail(
                                    Book.Detail.builder()
                                            .title(UUID.randomUUID().toString())
                                            .remark("postgresql json")
                                            .createTime(now)
                                            .updateTime(DateUtils.addDays(now, 1))
                                            .build()
                            )
                            .authors(Lists.newArrayList(
                                    Book.Author.builder().authorName("zhuzhenjie").age(18).build(),
                                    Book.Author.builder().authorName("John Smith").age(20).build()
                            ))
                            .build()
            );
        });

    }


    @Test
    public void testGetAllBooks() {
        List<Book> books = bookService.getAllBooks();
        books.forEach(System.out::println);
        Assertions.assertNotNull(books);
    }

    @Test
    public void testDeleteBook() {
        int i = bookService.deleteBook(id);
        Assertions.assertEquals(1, i);
    }


    @Test
    public void testLimitPage() {
        int pageSize = 10;
        Page<Book> bookPage = bookService.limitPage(2, pageSize);
        System.out.println("page.size is    " + pageSize);
        System.out.println("records.size is " + bookPage.getRecords().size());
    }

    @Test
    public void listReturnMap() {

        List<Map<String, Object>> maps = bookService.listReturnMap();

        System.out.println("BookServiceTest.listReturnMap");
    }

}
