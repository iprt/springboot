package org.iproute.springboot.design.tree.utils.recursion.tree;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TreeRecursionUtils 树形结构自上而下的递归处理封装
 *
 * @author zhuzhenjie
 * @since 2022/5/11
 */
@Builder
@Slf4j
public class TreeRecursionUtils<T> {

    private T root;

    TreeNodeOperator<T> nodeOperators;

    private TreeNodeExpandable<T> expandable;

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

        if (Objects.isNull(expandable)) {
            log.info("Need Listable for Recursion");
            return;
        }

        Predicate<T> p = this.expandable.getExpandable();
        if (Objects.isNull(p)) {
            return;
        }

        if (p.test(node)) {
            Consumer<T> nodeOperate = nodeOperators.getListableNodeOperate();
            if (Objects.nonNull(nodeOperate)) {
                nodeOperate.accept(node);
            }

            Function<T, List<T>> expand = expandable.getExpandFunc();

            if (Objects.isNull(expand)) {
                return;
            }
            List<T> nodes = expand.apply(node);
            if (CollectionUtils.isEmpty(nodes)) {
                return;
            }
            nodes.forEach(this::operate);
        } else {
            Consumer<T> childOperate = nodeOperators.getNodeOperate();
            if (Objects.nonNull(childOperate)) {
                childOperate.accept(node);
            }
        }
    }

    public void operateWithParent(T startParent) {
        operateWithParent(root, startParent);
    }

    private void operateWithParent(T node, T parent) {
        if (Objects.nonNull(filter) && filter.test(node)) {
            return;
        }

        if (Objects.isNull(expandable)) {
            log.info("Need Listable for Recursion");
            return;
        }

        Predicate<T> p = this.expandable.getExpandable();
        if (Objects.isNull(p)) {
            return;
        }

        if (p.test(node)) {
            BiConsumer<T, T> nodeOperateWithParent = nodeOperators.getListableNodeOperateWithParent();
            if (Objects.nonNull(nodeOperateWithParent)) {
                // 父节点传值的话需要自己扩展
                nodeOperateWithParent.accept(node, parent);
            }
            Function<T, List<T>> expand = this.expandable.getExpandFunc();
            if (Objects.isNull(expand)) {
                return;
            }
            List<T> nodes = expand.apply(node);
            if (CollectionUtils.isEmpty(nodes)) {
                return;
            }
            nodes.forEach(t -> {
                operateWithParent(t, node);
            });

        } else {
            BiConsumer<T, T> childOperateWithParent = nodeOperators.getNodeOperateWithParent();
            if (Objects.nonNull(childOperateWithParent)) {
                childOperateWithParent.accept(node, parent);
            }
        }
    }

}
