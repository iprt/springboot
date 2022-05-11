package org.iproute.springboot.design.mysqltree.test;

import org.iproute.springboot.design.mysqltree.utils.recursion.TreeRecursionUtils;
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
                .parentFunc(f -> {
                    System.out.println("folder:" + f.getAbsolutePath());
                })
                .childFunc(f -> {
                    System.out.println("file  :" + f.getAbsolutePath());
                })
                .listable(File::isDirectory)
                .listFunc(f -> {
                    File[] files = f.listFiles();
                    if (Objects.isNull(files)) {
                        return Collections.emptyList();
                    }
                    return Stream.of(files).collect(Collectors.toList());
                })
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
                .parentFuncWithParent((node, p) -> {
                    System.out.println("folder:" + node.getAbsolutePath() +
                            " parent:" + (Objects.isNull(p) ? "null" : p.getAbsolutePath()));
                })
                .childFuncWithParent((node, p) -> {
                    System.out.println("file  :" + node.getAbsolutePath() +
                            " parent:" + (Objects.isNull(p) ? "null" : p.getAbsolutePath()));
                })
                .listable(File::isDirectory)
                .listFunc(f -> {
                    File[] files = f.listFiles();
                    if (Objects.isNull(files)) {
                        return Collections.emptyList();
                    }
                    return Stream.of(files).collect(Collectors.toList());
                })
                .filter(null)
                .build();

        u.operateWithParent(null);
    }

}
