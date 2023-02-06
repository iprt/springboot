package org.iproute.springboot.entities.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Dept
 *
 * @author zhuzhenjie
 * @since 2021/11/27
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("dept")
public class Dept {
    private String id;
    private String name;
    private String pid;
}
