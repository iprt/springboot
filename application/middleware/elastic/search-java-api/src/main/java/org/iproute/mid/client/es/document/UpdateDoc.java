package org.iproute.mid.client.es.document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.EsOperator;

import java.util.HashMap;
import java.util.Map;

/**
 * UpdateDoc
 *
 * @author devops@kubectl.net
 * @since 2022/7/17
 */
public class UpdateDoc {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        EsOperator.operate(client -> {
            // UpdateRequest ur = new UpdateRequest(IndexConst.NAME, "1001");
            UpdateRequest ur = new UpdateRequest();
            // 配置修改参数
            ur.index(IndexConst.NAME).id("1001");
            // 设置请求体，对数据进行修改
            Map<String, Object> source = new HashMap<>(1);
            source.put("sex", "女");
            ur.doc(source, XContentType.JSON);

            UpdateResponse response = client.update(ur, RequestOptions.DEFAULT);

            log.info("_id : {}", response.getId());
            log.info("_index : {}", response.getIndex());
            log.info("_result : {}", response.getResult());
        });
    }
}
