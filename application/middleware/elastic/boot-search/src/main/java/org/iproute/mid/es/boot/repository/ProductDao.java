package org.iproute.mid.es.boot.repository;

import org.iproute.mid.es.boot.entities.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductDao
 *
 * @author devops@kubectl.net
 * @since 2022/7/18
 */
@Repository
public interface ProductDao extends ElasticsearchRepository<Product, Long> {
}
