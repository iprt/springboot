package org.iproute.mid.es.boot.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * ElasticsearchConfig
 *
 * @author zhuzhenjie
 * @since 2022/7/18
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Configuration
public class ElasticsearchConfig extends AbstractElasticsearchConfiguration {
    @Getter
    @Setter
    private String host;

    @Getter
    @Setter
    private Integer port;

    @Getter
    @Setter
    private String scheme;

    @Override
    public RestHighLevelClient elasticsearchClient() {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(host, port, scheme));
        return new RestHighLevelClient(builder);
    }
}
