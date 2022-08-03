package org.iproute.src.learn.anno;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * DemoBean
 *
 * @author zhuzhenjie
 * @since 2022/8/1
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@Component
public class DemoBean {
    private Long id;
    private String name;
}
