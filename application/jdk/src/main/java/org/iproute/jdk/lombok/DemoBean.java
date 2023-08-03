package org.iproute.jdk.lombok;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * DemoBean
 *
 * @author zhuzhenjie
 * @since 2023/8/2
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder
@Data
public class DemoBean {

    private Long id;

    private String name;

    private Date createTime;

    private Date updateTime;

}
