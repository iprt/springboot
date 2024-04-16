package org.iproute.mid.client.es.simplify;

import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.iproute.mid.client.es.config.EsConfig;

import java.io.IOException;

/**
 * EsAction
 *
 * @author devops@kubectl.net
 * @since 2022/7/17
 */
public class EsOperator {
    private static final Logger log = LogManager.getLogger();
    private static final EsConfig CONFIG = EsConfig.get();

    public static void operate(Action action) {
        // 创建客户端连接
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(CONFIG.getHost(), CONFIG.getPort(), CONFIG.getScheme()))
        );

        try {
            action.operate(client);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                log.error("close exception : ", e);
            }
        }
    }
}
