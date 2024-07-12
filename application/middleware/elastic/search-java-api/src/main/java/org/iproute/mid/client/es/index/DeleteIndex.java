package org.iproute.mid.client.es.index;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.iproute.mid.client.es.config.EsConfig;
import org.iproute.mid.client.es.config.IndexConst;

import java.io.IOException;

/**
 * ShowIndex
 *
 * @author tech@intellij.io
 * @since 2022/7/17
 */
public class DeleteIndex {

    private static final Logger log = LogManager.getLogger();
    private static final EsConfig CONFIG = EsConfig.get();

    public static void main(String[] args) throws IOException {
        // 创建客户端连接
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(CONFIG.getHost(), CONFIG.getPort(), CONFIG.getScheme()))
        );

        doDeleteIndex(client);

        client.close();
    }

    static void doDeleteIndex(RestHighLevelClient client) throws IOException {
        DeleteIndexRequest delete = new DeleteIndexRequest(IndexConst.NAME);
        AcknowledgedResponse response = client.indices().delete(delete, RequestOptions.DEFAULT);
        log.info("删除状态是 ：{}", response.isAcknowledged());
    }

}
