package org.iproute.mid.es.boot.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.mid.es.boot.config.IndexConst;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

/**
 * Product
 *
 * @author zhuzhenjie
 * @since 2022/7/18
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
// old config
// @Document(indexName = IndexConst.INDEX_NAME, shards = 1, replicas = 1)
@Document(indexName = IndexConst.INDEX_NAME)
@Setting(shards = 1, replicas = 0)
public class Product {

    /**
     * 必须有 id,这里的 id 是全局唯一的标识，等同于 es 中的"_id"
     */
    @Id
    private Long id;

    /**
     * type : 字段数据类型
     * analyzer : 分词器类型
     * index : 是否索引(默认:true)
     * Keyword : 短语,不进行分词
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;


    /**
     * 这个地方要记忆一下， keyword 是全词匹配哦
     */
    @Field(type = FieldType.Keyword)
    private String category;

    @Field(type = FieldType.Double)
    private Double price;

    /**
     * 图片的话，就不要索引了，所以 index = false
     */
    @Field(type = FieldType.Keyword, index = false)
    private String images;

}
