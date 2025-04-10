package org.iproute.mid.es.boot.document;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * RecordLog
 *
 * @author tech@intellij.io
 * @since 2025-04-10
 */
@Document(indexName = "record_log")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class RecordLog {

    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Date)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

    @Field(type = FieldType.Text)
    private Long productId;

    @Field(type = FieldType.Text)
    private String content;

}
