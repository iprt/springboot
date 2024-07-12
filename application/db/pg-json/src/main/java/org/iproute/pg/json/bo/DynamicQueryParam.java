package org.iproute.pg.json.bo;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * GroupQueryParam
 *
 * @author tech@intellij.io
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class DynamicQueryParam {

    public static final DynamicQueryParam queryStu = DynamicQueryParam.builder()
            .tableName("student").tableAlias("stu")
            .colMappings(
                    Lists.newArrayList(
                            DynamicQueryParam.ColMappings.builder().colName("id").colAlias("id").build(),
                            DynamicQueryParam.ColMappings.builder().colName("student_name").colAlias("studentName").build(),
                            DynamicQueryParam.ColMappings.builder().colName("create_time").colAlias("createTime").build()
                    )
            )
            .build();
    ;
    public static final DynamicQueryParam queryTeacher = DynamicQueryParam.builder()
            .tableName("teacher").tableAlias("teacher")
            .colMappings(
                    Lists.newArrayList(
                            DynamicQueryParam.ColMappings.builder().colName("id").colAlias("id").build(),
                            DynamicQueryParam.ColMappings.builder().colName("teacher_name").colAlias("teacherName").build(),
                            DynamicQueryParam.ColMappings.builder().colName("create_time").colAlias("createTime").build()
                    )
            )
            .build();
    ;


    private String tableName;
    private String tableAlias;

    List<ColMappings> colMappings;

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    @Data
    public static class ColMappings {
        private String colName;
        private String colAlias;
    }
}
