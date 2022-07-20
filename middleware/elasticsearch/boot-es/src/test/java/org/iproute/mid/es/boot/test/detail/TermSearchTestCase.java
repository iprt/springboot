package org.iproute.mid.es.boot.test.detail;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.iproute.mid.es.boot.config.IndexConst;
import org.iproute.mid.es.boot.entities.Product;
import org.iproute.mid.es.boot.test.BaseFrameTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * term是代表完全匹配，即不进行分词器分析，文档中必须包含整个搜索的词汇
 *
 * @author zhuzhenjie
 * @since 2022/7/18
 */
public class TermSearchTestCase extends BaseFrameTestCase {

    @Test
    public void term() {
        // 参数1
        NativeSearchQuery query = new NativeSearchQuery(
                QueryBuilders.matchQuery("title", "小米")
        );

        // 参数2
        Class<Product> clazz = Product.class;

        // 参数3
        IndexCoordinates ic = IndexCoordinates.of(IndexConst.INDEX_NAME);

        SearchHits<Product> hits = elasticsearchRestTemplate.search(
                query, clazz, ic
        );

        // 其实查的是正确的,只是没有全部拿出来!!! 我还在瞎定位
        System.out.println("hits.getTotalHits() : " + hits.getTotalHits());

        // hits.getSearchHits()
        //         .stream().map(SearchHit::getContent)
        //         .forEach(System.out::println);

        hits.getSearchHits().forEach(System.out::println);

    }

    @Test
    public void termPage() {
        // 参数1
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", "小米"))
                // 排序的写法
                .withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC))
                // 分页的写法
                .withPageable(PageRequest.of(9, 10))
                .build();

        // 分页的参数在这里

        // 参数2
        Class<Product> clazz = Product.class;

        // 参数3
        IndexCoordinates ic = IndexCoordinates.of(IndexConst.INDEX_NAME);

        SearchHits<Product> hits = elasticsearchRestTemplate.search(
                query, clazz, ic
        );

        // 其实查的是正确的,只是没有全部拿出来!!! 我还在瞎定位
        System.out.println("hits.getTotalHits() : " + hits.getTotalHits());

        hits.getSearchHits().forEach(System.out::println);
    }

}
