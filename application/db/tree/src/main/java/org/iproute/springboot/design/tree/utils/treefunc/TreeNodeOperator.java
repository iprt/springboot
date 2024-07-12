package org.iproute.springboot.design.tree.utils.treefunc;

import lombok.Getter;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * TreeNodeOperator
 *
 * @author tech@intellij.io
 * @since 2022/5/16
 */
@Getter
public class TreeNodeOperator<T> {

    /**
     * 可展开的节点操作
     */
    private Consumer<T> listableNodeOperate;

    /**
     * 不可展开的节点操作
     */
    private Consumer<T> nodeOperate;

    /**
     * 可展开的节点操作
     * <p>
     * 第一个参数 当前节点
     * <p>
     * 第二个参数 父节点
     */
    private BiConsumer<T, T> listableNodeOperateWithParent;

    /**
     * 不可展开的节点操作
     * <p>
     * 第一个参数 当前节点
     * <p>
     * 第二个参数 父节点
     */
    private BiConsumer<T, T> nodeOperateWithParent;

    public TreeNodeOperator(Consumer<T> listableNodeOperate, Consumer<T> nodeOperate) {
        this.listableNodeOperate = listableNodeOperate;
        this.nodeOperate = nodeOperate;
    }

    public TreeNodeOperator(BiConsumer<T, T> listableNodeOperateWithParent, BiConsumer<T, T> nodeOperateWithParent) {
        this.listableNodeOperateWithParent = listableNodeOperateWithParent;
        this.nodeOperateWithParent = nodeOperateWithParent;
    }


    /**
     * Without parent tree node operator.
     *
     * @param <T>                 the type parameter
     * @param listableNodeOperate 可开展节点的操作
     * @param nodeOperate         不可展开的节点的操作
     * @return the tree node operator
     */
    public static <T> TreeNodeOperator<T> withoutParent(Consumer<T> listableNodeOperate, Consumer<T> nodeOperate) {
        return new TreeNodeOperator<T>(listableNodeOperate, nodeOperate);
    }

    /**
     * With parent tree node operator.
     *
     * @param <T>                           the type parameter
     * @param listableNodeOperateWithParent 可开展节点的操作
     * @param nodeOperateWithParent         不可展开的节点的操作
     * @return the tree node operator
     */
    public static <T> TreeNodeOperator<T> withParent(BiConsumer<T, T> listableNodeOperateWithParent,
                                                     BiConsumer<T, T> nodeOperateWithParent) {
        return new TreeNodeOperator<T>(listableNodeOperateWithParent, nodeOperateWithParent);
    }


}
