package org.iproute.mid.es.boot.test.detail;

import org.iproute.mid.es.boot.entities.Product;
import org.iproute.mid.es.boot.test.BaseFrameTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * SimpleSearchTestCase
 *
 * @author devops@kubectl.net
 * @since 2022/7/18
 */
public class SimpleSearchTestCase extends BaseFrameTestCase {

    /**
     * postman :
     * <p>
     * post
     * <p>
     * {{es_server}}/product/_doc/2
     */
    @Test
    public void save() {
        Product p = Product.builder()
                .id(2L)
                .title("华为手机")
                .category("手机")
                .price(2999.0)
                .images("https://hello.world/huawei.jpg")
                .build();
        productDao.save(p);
    }


    /**
     * postman :
     * <p>
     * post
     * <p>
     * {{es_server}}/product/_doc/2
     */
    @Test
    public void update() {
        Product p = Product.builder()
                .id(2L)
                .title("小米手机")
                .category("手机")
                .price(6999.0)
                .images("https://hello.world/小米.jpg")
                .build();
        productDao.save(p);
    }

    /**
     * postman :
     * <p>
     * GET
     * <p>
     * {{es_server}}/product/2
     */
    @Test
    public void findById() {
        Optional<Product> product = productDao.findById(2L);
        product.ifPresent(System.out::println);
    }


    @Test
    public void findAll() {
        Iterable<Product> all = productDao.findAll();
        all.forEach(System.out::println);
    }

    @Test
    public void saveAll() {
        List<Product> ps = LongStream.range(1L, 101L)
                .mapToObj(i -> Product.builder()
                        .id(i)
                        .title("小米手机" + i)
                        .category("手机")
                        .price(1999.0 + i)
                        .images("https://hello.world/小米" + i + ".jpg")
                        .build())
                .collect(Collectors.toList());
        productDao.saveAll(ps);
    }

    /**
     * 分页查询 , 包含排序
     */
    @Test
    public void findPageable() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        // 当前页，第一页从 0 开始， 1 表示第二页
        int currentPage = 0;
        // 每页显示多少条
        int pageSize = 10;

        PageRequest pageRequest = PageRequest.of(currentPage, pageSize
                , sort);

        Page<Product> page = productDao.findAll(pageRequest);

        page.getContent().forEach(System.out::println);
    }

}
