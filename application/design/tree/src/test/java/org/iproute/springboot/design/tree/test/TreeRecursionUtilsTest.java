package org.iproute.springboot.design.tree.test;

import org.iproute.springboot.design.tree.utils.recursion.tree.TreeNodeExpandable;
import org.iproute.springboot.design.tree.utils.recursion.tree.TreeNodeOperator;
import org.iproute.springboot.design.tree.utils.recursion.tree.TreeRecursionUtils;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * RecursionUtilsTest
 *
 * @author zhuzhenjie
 * @since 2022/5/11
 */
public class TreeRecursionUtilsTest {

    @Test
    public void test() {
        File file = new File("D:/work");

        TreeRecursionUtils<File> u = TreeRecursionUtils.<File>builder()
                .root(file)
                .nodeOperators(TreeNodeOperator.withoutParent(
                        f -> System.out.println("folder:" + f.getAbsolutePath()),
                        f -> System.out.println("file  :" + f.getAbsolutePath())
                ))
                .expandable(TreeNodeExpandable.<File>builder()
                        .expandable(File::isDirectory)
                        .expandFunc(f -> {
                            File[] files = f.listFiles();
                            if (Objects.isNull(files)) {
                                return Collections.emptyList();
                            }
                            return Stream.of(files).collect(Collectors.toList());
                        })
                        .build())
                .filter(null)
                .build();

        u.operate();
        System.out.println();

    }

    @Test
    public void testWithParent() {
        File file = new File("D:/work");

        TreeRecursionUtils<File> u = TreeRecursionUtils.<File>builder()
                .root(file)
                .nodeOperators(TreeNodeOperator.withParent(
                        (node, p) -> {
                            System.out.println("folder:" + node.getAbsolutePath() +
                                    " parent:" + (Objects.isNull(p) ? "null" : p.getAbsolutePath()));
                        },
                        (node, p) -> {
                            System.out.println("file  :" + node.getAbsolutePath() +
                                    " parent:" + (Objects.isNull(p) ? "null" : p.getAbsolutePath()));
                        }
                ))
                .expandable(TreeNodeExpandable.<File>builder()
                        .expandable(File::isDirectory)
                        .expandFunc(f -> {
                            File[] files = f.listFiles();
                            if (Objects.isNull(files)) {
                                return Collections.emptyList();
                            }
                            return Stream.of(files).collect(Collectors.toList());
                        })
                        .build())
                .filter(null)
                .build();

        u.operateWithParent(null);
    }


    /**
     * 常规写法
     */
    @Test
    public void listFile() {
        listFile(new File("D:\\work"));
    }


    private void listFile(File f) {
        if (Objects.isNull(f) || !f.exists()) {
            return;
        }

        if (f.isDirectory()) {
            System.out.println("folder : " + f.getAbsolutePath());
            File[] files = f.listFiles();
            if (Objects.isNull(files)) {
                return;
            }
            Stream.of(files).forEach(this::listFile);

        } else {
            System.out.println("file  : " + f.getAbsolutePath());
        }
    }


}
