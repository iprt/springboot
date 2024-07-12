package org.iproute.mid.client.es.document;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.xcontent.XContentType;
import org.iproute.mid.client.es.config.IndexConst;
import org.iproute.mid.client.es.simplify.EsOperator;

import java.io.IOException;

/**
 * InsertDoc
 *
 * @author tech@intellij.io
 * @since 2022/7/17
 */
public class InsertDoc {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        EsOperator.operate((client) -> {

            // 新增文档，请求对象
            IndexRequest ir = new IndexRequest();
            // 设置索引（即mysql db） 和唯一ID
            ir.index(IndexConst.NAME).id("1001");

            // 创建插入的对象
            UserBean u = UserBean.builder()
                    .name("zhangsan").age(30).sex("男")
                    .build();

            ObjectMapper om = new ObjectMapper();
            String json = om.writeValueAsString(u);

            // 添加文档格式，数据指定为json格式
            ir.source(json, XContentType.JSON);

            // 客户端发送请求
            IndexResponse response = client.index(ir, RequestOptions.DEFAULT);

            // 打印结果信息
            log.info("_index = {}", response.getIndex());
            log.info("_id = {}", response.getId());
            log.info("_result = {}", response.getResult());

        });

    }
}
