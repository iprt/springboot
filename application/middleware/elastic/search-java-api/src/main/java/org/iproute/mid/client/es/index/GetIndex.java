package org.iproute.mid.client.es.index;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.iproute.mid.client.es.config.EsConfig;
import org.iproute.mid.client.es.config.IndexConst;

import java.io.IOException;

/**
 * ShowIndexes
 *
 * @author devops@kubectl.net
 * @since 2022/7/17
 */
public class GetIndex {
    private static final Logger log = LogManager.getLogger();
    private static final EsConfig CONFIG = EsConfig.get();

    public static void main(String[] args) throws IOException {
        // 创建客户端连接
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(CONFIG.getHost(), CONFIG.getPort(), CONFIG.getScheme()))
        );

        doShowIndex(client);

        client.close();
    }

    static void doShowIndex(RestHighLevelClient client) throws IOException {
        GetIndexRequest request = new GetIndexRequest(IndexConst.NAME);
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);

        log.info("response aliases : {}", response.getAliases());
        log.info("response mappings : {}", response.getMappings());
        log.info("response settings : {}", response.getSettings());
    }
}
