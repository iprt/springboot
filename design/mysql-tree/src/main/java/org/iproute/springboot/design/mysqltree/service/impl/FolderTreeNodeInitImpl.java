package org.iproute.springboot.design.mysqltree.service.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.design.mysqltree.model.TreeNode;
import org.iproute.springboot.design.mysqltree.service.FolderTreeNodeInit;
import org.iproute.springboot.design.mysqltree.service.TreeNodeService;
import org.iproute.springboot.design.mysqltree.utils.recursion.TreeNodeListable;
import org.iproute.springboot.design.mysqltree.utils.recursion.TreeNodeOperator;
import org.iproute.springboot.design.mysqltree.utils.recursion.TreeRecursionUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FolderTreeNodeInitImpl
 *
 * @author winterfell
 * @since 2022/4/20
 */
@Service
public class FolderTreeNodeInitImpl implements FolderTreeNodeInit {

    private final TreeNodeService treeNodeService;

    public FolderTreeNodeInitImpl(TreeNodeService treeNodeService) {
        this.treeNodeService = treeNodeService;
    }

    @Override
    public void init(String path) {
        FileTreeNodeHelper fh = FileTreeNodeHelper.builder()
                .file(new File(path))
                .id(-1L)
                .build();
        if (!fh.getFile().exists() || fh.getFile().isFile()) {
            return;
        }
        TreeRecursionUtils<FileTreeNodeHelper> u = TreeRecursionUtils.<FileTreeNodeHelper>builder()
                .root(fh)
                .nodeOperators(TreeNodeOperator.withParent(
                        (n, p) -> {
                            Long pid = p.getId();
                            String name = n.getFile().getAbsolutePath().substring(path.length());
                            if (StringUtils.isBlank(name)) {
                                name = "\\";
                            }
                            TreeNode treeNode = treeNodeService.addNode(pid, name);

                            // for child
                            Long id = treeNode.getId();
                            n.setId(id);
                        },
                        (n, p) -> {
                            Long pid = p.getId();
                            String name = n.getFile().getAbsolutePath().substring(path.length());
                            treeNodeService.addNode(pid, name);
                        }
                ))
                .listable(TreeNodeListable.<FileTreeNodeHelper>builder()
                        .listable(f -> f.getFile().isDirectory())
                        .expand(f -> {
                            File[] files = f.getFile().listFiles();
                            if (Objects.isNull(files)) {
                                return Collections.emptyList();
                            }
                            return Stream.of(files)
                                    .map(ff -> FileTreeNodeHelper.builder().file(ff).build())
                                    .collect(Collectors.toList());
                        })
                        .build())
                .filter(f -> {
                    String[] prefixIgnored = new String[]{
                            ".git", ".idea", "@", "#", "node_modules", "target", "out"
                    };

                    String[] suffixIgnored = new String[]{
                            ".class"
                    };

                    for (String s : prefixIgnored) {
                        if (f.getFile().getName().startsWith(s)) {
                            return true;
                        }
                    }
                    for (String e : suffixIgnored) {
                        if (f.getFile().getName().endsWith(e)) {
                            return true;
                        }
                    }
                    return false;
                })
                .build();

        u.operateWithParent(FileTreeNodeHelper.builder().id(-1L).build());
    }


    @Builder
    private static class FileTreeNodeHelper {

        @Getter
        private File file;

        @Setter
        @Getter
        private Long id;

    }

}
