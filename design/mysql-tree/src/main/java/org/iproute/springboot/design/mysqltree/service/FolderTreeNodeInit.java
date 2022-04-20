package org.iproute.springboot.design.mysqltree.service;

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
     * @param path the folder
     */
    void init(String path);
}
