package org.iproute.mid.client.es.document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.RequestOptions;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.EsOperator;

import java.io.IOException;

/**
 * DeleteDoc
 *
 * @author zhuzhenjie
 * @since 2022/7/17
 */
public class DeleteDoc {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) throws IOException {
        EsOperator.operate(client -> {

            DeleteRequest dr = new DeleteRequest();
            dr.index(IndexConst.NAME).id("1001");

            DeleteResponse response = client.delete(dr, RequestOptions.DEFAULT);

            log.info("response : {}", response);
        });
    }
}
