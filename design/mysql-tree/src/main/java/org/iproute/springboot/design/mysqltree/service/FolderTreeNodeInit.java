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
     * @param basePid 基础PID
     * @param path    the folder
     */
    void init(Long basePid, String path);

    /**
     * Query.
     *
     * @param id the id
     */
    void query(Long id);
}
