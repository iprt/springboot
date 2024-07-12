package org.iproute.mid.client.es.index;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.iproute.mid.client.es.config.EsConfig;
import org.iproute.mid.client.es.config.IndexConst;

import java.io.IOException;

/**
 * CreateIndex
 *
 * @author tech@intellij.io
 * @since 2022/7/17
 */
public class CreateIndex {
    private static final Logger log = LogManager.getLogger();
    private static final EsConfig CONFIG = EsConfig.get();

    public static void main(String[] args) throws IOException {
        // 创建客户端连接
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(CONFIG.getHost(), CONFIG.getPort(), CONFIG.getScheme()))
        );

        doCreateIndex(client);

        client.close();
    }


    static void doCreateIndex(RestHighLevelClient client) throws IOException {
        // 创建索引 - 请求对象
        CreateIndexRequest request = new CreateIndexRequest(IndexConst.NAME);

        // 发送请求，获取响应
        CreateIndexResponse response = client.indices().create(
                request
                , RequestOptions.DEFAULT);


        boolean ack = response.isAcknowledged();
        log.info("操作状态： {}", ack);
    }

}
