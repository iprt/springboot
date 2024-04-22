package org.iproute.pg.json.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * GroupQueryParam
 *
 * @author devops@kubectl.net
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class GroupQueryParam {
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
