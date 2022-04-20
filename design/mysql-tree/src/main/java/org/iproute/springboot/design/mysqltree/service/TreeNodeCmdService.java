package org.iproute.springboot.design.mysqltree.service;

import org.iproute.springboot.design.mysqltree.model.TreeNode;

import java.util.List;

/**
 * TreeNodeCommandQueryService
 *
 * @author winterfell
 * @since 2022/4/20
 */
public interface TreeNodeCmdService {

    /**
     * Query list.
     *
     * @param option  the option
     * @param type    the type
     * @param id      the id
     * @param pid     the pid
     * @param name    the name
     * @param level   the level
     * @param include include
     * @return the list
     */
    List<TreeNode> command(String option, String type,
                           Long id, Long pid, String name,
                           boolean include, int level);

}
