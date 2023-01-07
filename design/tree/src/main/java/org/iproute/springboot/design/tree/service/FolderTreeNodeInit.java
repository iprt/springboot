package org.iproute.springboot.design.tree.service;

/**
 * FolderTreeNodeInit
 *
 * @author winterfell
 * @since 2022/4/20
 */
public interface FolderTreeNodeInit {

    /**
     * Init.
     *
     * @param treeNodeService the tree node service
     * @param basePid         基础PID
     * @param path            the folder
     */
    void init(TreeNodeService treeNodeService,
              Long basePid, String path);


    /**
     * Query.
     *
     * @param treeNodeService the tree node service
     * @param id              the id
     */
    void logPrintQuery(TreeNodeService treeNodeService,
                       Long id);
}
