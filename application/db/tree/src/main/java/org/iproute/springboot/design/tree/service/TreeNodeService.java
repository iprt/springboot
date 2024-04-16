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
 * @author devops@kubectl.net
 * @since 2022/4/19
 */
public interface TreeNodeService {
    int ROOT_LEVEL = 1;

    /**
     * Retrieves information about a tree node.
     *
     * @param id the ID of the tree node to retrieve information for
     * @return the TreeNode object representing the tree node with the specified ID
     */
    TreeNode nodeInfo(long id);

    /**
     * Returns a list of TreeNodes at the specified level.
     *
     * @param level the level of the TreeNodes to retrieve
     * @return a list of TreeNodes at the specified level
     */
    List<TreeNode> levelNodes(int level);

    /**
     * Retrieves a list of parent TreeNodes for a given ID.
     *
     * @param id      the ID of the TreeNode to retrieve parents for
     * @param include a flag indicating whether or not to include the TreeNode with the given ID in the result list
     * @param level   the number of levels up the tree to retrieve parents from (including the initial TreeNode)
     * @return a list of parent TreeNodes at the specified level, if any; otherwise an empty list
     */
    List<TreeNode> parents(Long id, boolean include, int level);

    /**
     * Retrieves the parent TreeNode for a given ID.
     *
     * @param id the ID of the TreeNode
     * @return the parent TreeNode object of the specified ID
     */
    TreeNode parent(Long id);

    /**
     * Retrieves a list of child TreeNodes for a given ID, optionally including the TreeNode with the given ID.
     *
     * @param id      the ID of the parent TreeNode
     * @param include a flag indicating whether or not to include the TreeNode with the given ID in the result list
     * @param level   the number of levels down the tree to retrieve children from (including the initial TreeNode)
     * @return a list of child TreeNodes at the specified level, if any; otherwise an empty list
     */
    List<TreeNode> children(Long id, boolean include, int level);

    /**
     * Adds a new TreeNode to the tree.
     *
     * @param pid       the parent ID of the new node
     * @param nodeName  the name of the new node
     * @return the newly created TreeNode
     */
    TreeNode addNode(Long pid, String nodeName);

    /**
     * Adds multiple TreeNodes with the given parent ID and node names.
     *
     * @param pid       the parent ID of the new nodes
     * @param nodeNames the list of node names to be added
     * @return a list of the newly created TreeNodes
     */
    List<TreeNode> addNodes(Long pid, List<String> nodeNames);

    /**
     * Removes a TreeNode with the specified ID, optionally including its children.
     *
     * @param id      the ID of the TreeNode to be removed
     * @param include a flag indicating whether or not to include the children of the TreeNode with the given ID
     * @return the number of TreeNodes that were removed
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
     * Removes all TreeNodes at the specified level, optionally including their children.
     *
     * @param level    the level of the TreeNodes to be removed
     * @return the number of TreeNodes that were removed
     */
    int removeLevel(int level);


    /**
     * Resets the state of the TreeNodeService.
     * This method clears any existing data and sets the service back to its initial state.
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
