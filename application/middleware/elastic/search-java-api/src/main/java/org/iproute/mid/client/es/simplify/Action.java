package org.iproute.mid.client.es.simplify;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * Action
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
@FunctionalInterface
public interface Action {
    /**
     * Do operate.
     *
     * @param client the client
     * @throws Exception the exception
     */
    void operate(RestHighLevelClient client) throws Exception;
}
