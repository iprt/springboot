package org.iproute.springboot.config.mvc.req;

import lombok.*;

/**
 * PageReq
 *
 * @author zhuzhenjie
 * @since 2022/1/23
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class PageReq {
    private Integer pageNum;
    private Integer pageSize;
    /**
     * true: 查询总数 性能低
     * <p>
     * false： 不查询总数 性能高
     */
    private Boolean searchCount;
}
