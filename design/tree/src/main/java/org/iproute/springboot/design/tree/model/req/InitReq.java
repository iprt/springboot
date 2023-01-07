package org.iproute.springboot.design.tree.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * InitReq
 *
 * @author winterfell
 * @since 2022/4/20
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class InitReq {
    private String ds;
    private String path;

    private Long id;
    private Long basePid;
}
