package org.iproute.springboot.design.tree.service;

import org.iproute.springboot.design.tree.model.tree.TreeNode;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * TreeNodeService
 *
 * @author winterfell
 * @since 2022/4/19
 */
public interface TreeNodeService {
    int ROOT_LEVEL = 1;

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
     * 查询父节点继承树
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
     * @return the int
     */
    int removeNode(Long id, boolean include);

    /**
     * Remove.
     *
     * @param id the id
     * @return the int
     */
    default int removeChildNodes(Long id) {
        return this.removeNode(id, false);
    }

    /**
     * 删除某一层，（理论意义上删除所有的子节点）
     *
     * @param level the level
     */
    int removeLevel(int level);


    /**
     * 重置
     */
    void reset();


    /**
     * 子类List 转 父类List
     *
     * @param <T>            the type parameter
     * @param childTreeNodes the child tree nodes
     * @return the list
     */
    default <T extends TreeNode> List<TreeNode> castToTreeNodeList(List<T> childTreeNodes) {
        return childTreeNodes.stream().map(node -> (TreeNode) node).collect(Collectors.toList());
    }


    /**
     * 如果为空返回空列表
     *
     * @param <T>   the type parameter
     * @param nodes the nodes
     * @return the list
     */
    default <T extends TreeNode> List<TreeNode> returnTreeNodeList(List<T> nodes) {
        return Objects.isNull(nodes) || CollectionUtils.isEmpty(nodes)
                ? Collections.emptyList()
                : castToTreeNodeList(nodes);
    }

}
