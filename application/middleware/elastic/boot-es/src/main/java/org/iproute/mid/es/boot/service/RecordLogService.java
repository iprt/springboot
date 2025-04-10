package org.iproute.mid.es.boot.service;

import org.iproute.mid.es.boot.document.RecordLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * RecordLogService
 *
 * @author tech@intellij.io
 * @since 2025-04-10
 */
public interface RecordLogService extends ElasticsearchRepository<RecordLog, String> {

}
