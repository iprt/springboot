package org.iproute.springboot.design.mysqltree.utils.recursion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * NodeWithParent
 *
 * @author zhuzhenjie
 * @since 2022/5/11
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class RichNode<T> {
    private T node;
    private T parent;
}
