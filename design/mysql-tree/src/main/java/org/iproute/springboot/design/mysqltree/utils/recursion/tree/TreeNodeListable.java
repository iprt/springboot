package org.iproute.springboot.design.mysqltree.utils.recursion.tree;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TreeListable
 *
 * @author zhuzhenjie
 * @since 2022/5/16
 */
@Builder
@Getter
public class TreeNodeListable<T> {
    private Predicate<T> listable;
    private Function<T, List<T>> expand;

    private TreeNodeListable(Predicate<T> listable, Function<T, List<T>> expand) {
        this.listable = listable;
        this.expand = expand;
    }
}
