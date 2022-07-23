package org.iproute.mid.client.es.hello;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.iproute.mid.client.es.config.EsConfig;

import java.io.IOException;

/**
 * HelloElasticSearch
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
public class HelloElasticsearch {
    private static final Logger log = LogManager.getLogger();
    private static final EsConfig CONFIG = EsConfig.get();


    public static void main(String[] args) throws IOException {

        // 创建客户端连接
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(CONFIG.getHost(), CONFIG.getPort(), CONFIG.getScheme()))
        );

        log.info("client : {}", client);

        // 关闭客户端连接
        client.close();
    }

}
