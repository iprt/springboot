package org.iproute.springboot.design.tree.utils.treefunc;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TreeListable
 *
 * @author tech@intellij.io
 * @since 2022/5/16
 */
@Builder
@Getter
public class TreeNodeExpandable<T> {
    /**
     * 展开的判断方法
     */
    private Predicate<T> expandable;

    /**
     * 是否可展开
     */
    private Function<T, List<T>> expandFunc;

    private TreeNodeExpandable(Predicate<T> expandable, Function<T, List<T>> expandFunc) {
        this.expandable = expandable;
        this.expandFunc = expandFunc;
    }
}
