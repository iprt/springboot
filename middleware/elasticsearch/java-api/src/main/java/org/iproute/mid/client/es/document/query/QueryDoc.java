package org.iproute.mid.client.es.document.query;

import cn.hutool.core.util.StrUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.Action;
import org.iproute.mid.client.es.simplify.EsOperator;

import java.util.function.Consumer;

/**
 * QueryDoc
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
public class QueryDoc {
    private static final Logger log = LogManager.getLogger();

    private static final Consumer<SearchResponse> PRINT = response -> {

        SearchHits hits = response.getHits();
        // 查询匹配
        log.info("took : {}", response.getTook());
        log.info("timeout : {}", response.isTimedOut());
        log.info("totalHits : {}", hits.getTotalHits());
        log.info("max score : {}", hits.getMaxScore());

        log.info("==========");

        hits.forEach(hit -> {
            log.info("source as string : {}", hit.getSourceAsString());
        });
        log.info("==========");
    };

    /**
     * 查询所有
     */
    private static final Action QUERY_ALL = client -> {
        SearchRequest req = new SearchRequest();
        req.indices(IndexConst.NAME);

        // 构建查询的请求体
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        // 查询所有数据
        ssb.query(QueryBuilders.matchAllQuery());

        // put ssb in req
        req.source(ssb);

        SearchResponse response = client.search(req, RequestOptions.DEFAULT);

        PRINT.accept(response);
    };


    /**
     * 分页查询
     */
    private static Action QUERY_PAGE = client -> {
        // 创建搜索请求对象
        SearchRequest request = new SearchRequest();
        // 索引
        request.indices("shopping");

        request.source(SearchSourceBuilder.searchSource()
                .query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(2)
        );

        SearchResponse response = client.search(request, RequestOptions.DEFAULT);

        PRINT.accept(response);
    };


    /**
     * 查询排序
     */
    private static Action QUERY_SORT = client -> {
        SearchRequest sr = new SearchRequest("shopping");

        sr.source(SearchSourceBuilder.searchSource()
                .query(
                        QueryBuilders.boolQuery()
                                .should(
                                        QueryBuilders.matchQuery("category", "小米")
                                )
                                .should(
                                        QueryBuilders.matchQuery("category", "华为")
                                )
                )
                .sort("price", SortOrder.DESC)
                .from(0)
                .size(2)
        );

        SearchResponse response = client.search(sr, RequestOptions.DEFAULT);

        PRINT.accept(response);
    };


    private static final Action QUERY_CONDITIONS = client -> {


        PRINT.accept(client.search(
                new SearchRequest(IndexConst.NAME)
                        .source(SearchSourceBuilder.searchSource()
                                .query(QueryBuilders.boolQuery()
                                                // .must(QueryBuilders.matchQuery(
                                                //         "age", "30"
                                                // ))
                                                .mustNot(QueryBuilders.matchQuery(
                                                        "name", "zhangsan"
                                                ))
                                        // .should(QueryBuilders.matchQuery(
                                        //         "sex", "男"
                                        // ))
                                )
                        )
                , RequestOptions.DEFAULT
        ));

    };


    public static final Action QUERY_RANGE = client -> {
        PRINT.accept(
                client.search(
                        new SearchRequest("shopping")
                                .source(SearchSourceBuilder.searchSource()
                                        .query(QueryBuilders.rangeQuery("price")
                                                .gte(2000)
                                        ))
                        , RequestOptions.DEFAULT
                )
        );
    };


    public static void main(String[] args) {
        EsOperator.operate(QUERY_ALL);
        log.info("{}{}{}", StrUtil.CRLF, StrUtil.CRLF, StrUtil.CRLF);

        EsOperator.operate(QUERY_PAGE);
        log.info("{}{}{}", StrUtil.CRLF, StrUtil.CRLF, StrUtil.CRLF);

        EsOperator.operate(QUERY_SORT);
        log.info("{}{}{}", StrUtil.CRLF, StrUtil.CRLF, StrUtil.CRLF);

        EsOperator.operate(QUERY_CONDITIONS);
        log.info("{}{}{}", StrUtil.CRLF, StrUtil.CRLF, StrUtil.CRLF);

        EsOperator.operate(QUERY_RANGE);
        log.info("{}{}{}", StrUtil.CRLF, StrUtil.CRLF, StrUtil.CRLF);

    }
}
