package org.iproute.springboot.design.mysqltree.utils.recursion;

/**
 * NodeOperatorWithParent
 *
 * @author zhuzhenjie
 * @since 2022/5/11
 */
@FunctionalInterface
public interface NodeOperatorWithParent<T> {
    /**
     * Operate.
     *
     * @param node   当前节点信息
     * @param parent 父节点信息
     */
    void accept(T node, T parent);
}
