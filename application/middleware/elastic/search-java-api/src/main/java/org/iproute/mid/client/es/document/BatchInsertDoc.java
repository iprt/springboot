package org.iproute.mid.client.es.document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.EsOperator;

/**
 * BatchInsertDoc
 *
 * @author tech@intellij.io
 * @since 2022/7/17
 */
public class BatchInsertDoc {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        EsOperator.operate((client) -> {
            BulkRequest br = new BulkRequest();

            br.add(new
                    IndexRequest().index(IndexConst.NAME).id("1001").source(XContentType.JSON, "name",
                    "zhangsan"));
            br.add(new
                    IndexRequest().index(IndexConst.NAME).id("1002").source(XContentType.JSON, "name",
                    "lisi"));
            br.add(new
                    IndexRequest().index(IndexConst.NAME).id("1003").source(XContentType.JSON, "name",
                    "wangwu"));


            // 客户端发送请求，获取响应对象
            BulkResponse responses = client.bulk(br, RequestOptions.DEFAULT);
            // 打印结果信息
            log.info("took: {}", responses.getTook());
            log.info("items: {}", (Object[]) responses.getItems());

        });

    }
}