package org.iproute.springboot.design.mysqltree.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.iproute.springboot.design.mysqltree.model.TreeNode;
import org.iproute.springboot.design.mysqltree.service.FolderTreeNodeInit;
import org.iproute.springboot.design.mysqltree.service.TreeNodeService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

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
        File folder = new File(path);
        if (!folder.exists() || folder.isFile()) {
            return;
        }
        this.recursionList(folder, 0, -1L, path);
    }

    private void recursionList(File file, int spaceTimes, Long pid, String path) {
        if (ignore(file.getName(),
                new String[]{
                        ".git", ".idea", "@", "#", "node_modules", "target", "out"
                }, new String[]{
                        ".class"
                })
        ) {
            return;
        }

        if (file.isFile()) {
            String fileName = file.getAbsolutePath().substring(path.length());
            // System.out.println(space(spaceTimes) + fileName);
            TreeNode folderNode = treeNodeService.addNode(pid, fileName);
        }
        if (file.isDirectory()) {
            String folderName = file.getAbsolutePath().substring(path.length());
            // System.out.println(space(spaceTimes) + folderName);
            if (StringUtils.isBlank(folderName)) {
                folderName = "\\";
            }
            TreeNode folderNode = treeNodeService.addNode(pid, folderName);
            Long newPid = folderNode.getId();

            File[] files = file.listFiles();
            if (Objects.isNull(files)) {
                return;
            }

            for (File tmpFile : files) {
                recursionList(tmpFile, spaceTimes + 1, newPid, path);
            }
        }
    }


    private String space(int level) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < level; i++) {
            builder.append("  ");
        }
        return builder.toString();
    }

    private boolean ignore(String fileName, String[] startIgnore, String[] endIgnore) {
        for (String s : startIgnore) {
            if (fileName.startsWith(s)) {
                return true;
            }
        }
        for (String e : endIgnore) {
            if (fileName.endsWith(e)) {
                return true;
            }
        }
        return false;
    }

}
