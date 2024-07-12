package org.iproute.mid.client.es.document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.EsOperator;

import java.io.IOException;

/**
 * GetDoc
 *
 * @author tech@intellij.io
 * @since 2022/7/17
 */
public class GetDoc {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        EsOperator.operate((client) -> {

            GetRequest get = new GetRequest().index(IndexConst.NAME).id("1001");

            GetResponse getRsp = client.get(get, RequestOptions.DEFAULT);

            log.info("get response is :{}",getRsp);

        });
    }
}
