package org.iproute.springboot.design.tree.service;

import org.iproute.springboot.design.tree.model.tree.TreeNode;

import java.util.List;

/**
 * TreeNodeCommandQueryService
 *
 * @author zhuzhenjie
 * @since 2022/4/20
 */
public interface TreeNodeCmdService {

    /**
     * Query list.
     *
     * @param treeNodeService 选择数据源
     * @param option          the option
     * @param type            the type
     * @param id              the id
     * @param pid             the pid
     * @param name            the name
     * @param include         include
     * @param level           the level
     * @return the list
     */
    List<TreeNode> command(TreeNodeService treeNodeService,
                           String option, String type,
                           Long id, Long pid, String name,
                           boolean include, int level);

}
