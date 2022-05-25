package org.iproute.springboot.design.mysqltree.service;

import org.iproute.springboot.design.mysqltree.model.TreeNode;

import java.util.List;

/**
 * TreeNodeService
 *
 * @author winterfell
 * @since 2022/4/19
 */
public interface TreeNodeService {

    /**
     * Node info list.
     *
     * @param id the id
     * @return the list
     */
    TreeNode nodeInfo(long id);

    /**
     * 获取某一层的所有的treeNode
     *
     * @param level the level
     * @return the list
     */
    List<TreeNode> levelNodes(int level);

    /**
     * 查询父节点继承数
     *
     * @param id      id
     * @param include 是否包含当前节点
     * @param level   向上查询父节点的层数
     * @return 父节点继承数 list
     */
    List<TreeNode> parents(Long id, boolean include, int level);

    /**
     * 查询单个父节点
     *
     * @param id the id
     * @return 父节点
     */
    TreeNode parent(Long id);

    /**
     * Child nodes list.
     *
     * @param id      the id
     * @param include 是否包含当前节点
     * @param level   查询子节点的层数 最小 1 层
     * @return the list
     */
    List<TreeNode> children(Long id, boolean include, int level);

    /**
     * 添加节点
     *
     * @param pid      id < 0 时：添加一层节点，否则添加子节点，这个行为是个约定
     * @param nodeName the node name
     * @return the tree node
     */
    TreeNode addNode(Long pid, String nodeName);

    /**
     * Add nodes tree node.
     *
     * @param pid       the pid
     * @param nodeNames the node names
     * @return the tree node
     */
    List<TreeNode> addNodes(Long pid, List<String> nodeNames);

    /**
     * Remove.
     *
     * @param id      the id
     * @param include the include
     */
    void removeNode(Long id, boolean include);

    /**
     * Remove.
     *
     * @param id the id
     */
    void removeChildNodes(Long id);

    /**
     * 删除某一层，（理论意义上删除所有的子节点）
     *
     * @param level the level
     */
    void removeLevel(int level);

}
