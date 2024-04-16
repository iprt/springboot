package org.iproute.springboot.design.tree.service.impl;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.design.tree.model.tree.TreeNode;
import org.iproute.springboot.design.tree.service.FolderTreeNodeInit;
import org.iproute.springboot.design.tree.service.TreeNodeService;
import org.iproute.springboot.design.tree.utils.treefunc.TreeNodeExpandable;
import org.iproute.springboot.design.tree.utils.treefunc.TreeNodeOperator;
import org.iproute.springboot.design.tree.utils.treefunc.TreeRecursionUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * FolderTreeNodeInitImpl
 *
 * @author devops@kubectl.net
 * @since 2022/4/20
 */
@Service
@Slf4j
public class FolderTreeNodeInitImpl implements FolderTreeNodeInit {


    @Override
    public void init(TreeNodeService treeNodeService,
                     Long basePid, String path) {
        FileTreeNodeHelper fh = FileTreeNodeHelper.builder()
                .file(new File(path))
                .id(null)
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
                .expandable(TreeNodeExpandable.<FileTreeNodeHelper>builder()
                        .expandable(f -> f.getFile().isDirectory())
                        .expandFunc(f -> {
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

        u.operateWithParent(FileTreeNodeHelper.builder()
                .id(Objects.isNull(basePid) ? -1L : basePid).build());

        log.info("init finished");
    }


    @Override
    public void logPrintQuery(TreeNodeService treeNodeService,
                              Long id) {

        TreeNode treeNode = treeNodeService.nodeInfo(id);

        if (Objects.isNull(treeNode)) {
            log.error("treeNode does not exists id = {}", id);
            return;
        }

        TreeRecursionUtils<TreeNode> u = TreeRecursionUtils.<TreeNode>builder()
                .root(treeNode)
                .nodeOperators(TreeNodeOperator.withoutParent(
                        ln -> log.info("listable node: {}", ln),
                        ln -> log.info("simple   node: {}", ln)
                ))
                .expandable(TreeNodeExpandable.<TreeNode>builder()
                        .expandable(n -> {
                            long nodeId = n.getId();
                            return !treeNodeService.children(nodeId, false, 1).isEmpty();
                        })
                        .expandFunc(n -> treeNodeService.children(n.getId(), false, 1))
                        .build())
                .build();
        u.operate();
    }


    @Builder
    @Getter
    private static class FileTreeNodeHelper {

        private File file;

        @Setter
        @Getter
        private Long id;

    }

}
