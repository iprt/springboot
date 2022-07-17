package org.iproute.mid.client.es.document.query;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.Action;

import java.util.Map;
import java.util.function.Consumer;

/**
 * QueryDoc
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
public class QueryDoc {
    public static final Logger log = LogManager.getLogger();

    public static final Consumer<SearchResponse> SHOW_RESPONSE = response -> {

        SearchHits hits = response.getHits();
        // 查询匹配
        log.info("took : {}", response.getTook());
        log.info("timeout : {}", response.isTimedOut());
        log.info("totalHits : {}", hits.getTotalHits());
        log.info("max score : {}", hits.getMaxScore());

        log.info("==========");

        hits.forEach(hit -> {
            log.info("source as string : {}", hit.getSourceAsString());
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (CollectionUtil.isNotEmpty(highlightFields)) {
                log.info("highlightFields : {}", highlightFields);
            }
        });
        log.info("==========");
    };

    /**
     * 查询所有
     */
    public static final Action QUERY_ALL = client -> {
        SearchRequest req = new SearchRequest();
        req.indices(IndexConst.NAME);

        // 构建查询的请求体
        SearchSourceBuilder ssb = new SearchSourceBuilder();
        // 查询所有数据
        ssb.query(QueryBuilders.matchAllQuery());

        // put ssb in req
        req.source(ssb);

        SearchResponse response = client.search(req, RequestOptions.DEFAULT);

        SHOW_RESPONSE.accept(response);
    };


    /**
     * 分页查询
     */
    public static Action QUERY_PAGE = client -> {
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

        SHOW_RESPONSE.accept(response);
    };


    /**
     * 查询排序
     */
    public static Action QUERY_SORT = client -> {
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

        SHOW_RESPONSE.accept(response);
    };


    public static final Action QUERY_CONDITIONS = client -> {


        SHOW_RESPONSE.accept(client.search(
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

    /**
     * 范围查询
     */
    public static final Action QUERY_RANGE = client -> {
        SHOW_RESPONSE.accept(
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

    /**
     * 模糊查询
     */
    public static final Action QUERY_FUZZY = client -> {

        SearchRequest request = new SearchRequest(IndexConst.NAME);
        SearchSourceBuilder ssb = SearchSourceBuilder.searchSource()
                .query(
                        QueryBuilders.fuzzyQuery("name", "wangwu")
                                .fuzziness(Fuzziness.ONE)
                );

        request.source(ssb);

        SHOW_RESPONSE.accept(client.search(request, RequestOptions.DEFAULT));
    };


    /**
     * 高亮
     */
    public static final Action QUERY_HIGHLIGHT = client -> {
        SearchRequest request = new SearchRequest(IndexConst.NAME);
        SearchSourceBuilder ssb = SearchSourceBuilder.searchSource()
                .query(
                        QueryBuilders.fuzzyQuery("name", "zhangsan")
                                .fuzziness(Fuzziness.ONE)
                );

        ssb.highlighter(new HighlightBuilder()
                .preTags("<font color=red>")
                .postTags("</font>")
                // 设置高亮字段
                .field("name")
        );

        request.source(ssb);

        SHOW_RESPONSE.accept(client.search(request, RequestOptions.DEFAULT));
    };


    /**
     * 最大值查询
     */
    public static final Action QUERY_MAX = client -> {
        SearchRequest request = new SearchRequest("shopping");

        SearchSourceBuilder ssb = SearchSourceBuilder.searchSource()
                .aggregation(AggregationBuilders.max("maxPrice")
                        .field("price")
                );

        request.source(ssb);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response : {}", response);
    };

    /**
     * 分组查询
     */
    public static final Action QUERY_GROUP = client -> {
        SearchRequest request = new SearchRequest("shopping");

        SearchSourceBuilder ssb = SearchSourceBuilder.searchSource()
                .aggregation(AggregationBuilders
                        .terms("price_group")
                        .field("price")
                );

        request.source(ssb);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        log.info("response : {}", response);
    };
}
