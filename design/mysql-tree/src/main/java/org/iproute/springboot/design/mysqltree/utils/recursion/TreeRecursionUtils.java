package org.iproute.springboot.design.mysqltree.utils.recursion;

import lombok.Builder;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TreeRecursionUtils 树形结构递归处理封装
 *
 * @author zhuzhenjie
 * @since 2022/5/11
 */
@Builder
public class TreeRecursionUtils<T> {

    private T root;

    /**
     * 父节点处理
     */
    private Consumer<T> parentFunc;

    private NodeOperatorWithParent<T> parentFuncWithParent;

    /**
     * 子节点处理
     */
    private Consumer<T> childFunc;

    private NodeOperatorWithParent<T> childFuncWithParent;

    /**
     * 节点判断
     */
    private Predicate<T> listable;

    /**
     * 基于父节点遍历出子节点
     */
    private Function<T, List<T>> listFunc;

    /**
     * 节点过滤
     */
    private Predicate<T> filter;


    public void operate() {
        operate(root);
    }

    private void operate(T node) {
        if (Objects.nonNull(filter) && filter.test(node)) {
            return;
        }
        boolean listable = this.listable.test(node);
        if (listable) {
            if (Objects.nonNull(parentFunc)) {
                parentFunc.accept(node);
            }
            List<T> nodes = listFunc.apply(node);
            nodes.forEach(this::operate);
        } else {
            if (Objects.nonNull(childFunc)) {
                childFunc.accept(node);
            }
        }
    }

    public void operateWithParent(T startParent) {
        operateWithParent(root, startParent);
    }

    private void operateWithParent(T node, T p) {
        if (Objects.nonNull(filter) && filter.test(node)) {
            return;
        }
        boolean listable = this.listable.test(node);
        if (listable) {
            if (Objects.nonNull(parentFuncWithParent)) {
                // 父节点传值的话需要自己扩展
                parentFuncWithParent.accept(node, p);
            }

            List<T> nodes = listFunc.apply(node);

            nodes.forEach(t -> {
                operateWithParent(t, node);
            });

        } else {
            if (Objects.nonNull(childFuncWithParent)) {
                childFuncWithParent.accept(node, p);
            }
        }
    }


}
