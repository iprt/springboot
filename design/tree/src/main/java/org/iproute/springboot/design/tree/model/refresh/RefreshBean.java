package org.iproute.springboot.design.tree.model.refresh;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * RefreshBean
 *
 * @author zhuzhenjie
 * @since 2022/12/3
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class RefreshBean {
    private String uuid;
    private Date date;
}
