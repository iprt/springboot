package org.iproute.springboot.design.mysqltree.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.design.mysqltree.mapper.TreeNodeMapper;
import org.iproute.springboot.design.mysqltree.model.TreeNode;
import org.iproute.springboot.design.mysqltree.service.TreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * TreeNodeServiceImpl
 *
 * @author winterfell
 * @since 2022/4/19
 */
@Service
@Slf4j
public class TreeNodeServiceImpl implements TreeNodeService {
    private static final int ROOT_LEVEL = 1;

    /**
     * transaction
     */
    @Autowired
    private TreeNodeService treeNodeService;

    @Autowired
    private TreeNodeMapper treeNodeMapper;

    @Override
    public TreeNode nodeInfo(long id) {
        return treeNodeMapper.nodeInfo(id);
    }

    @Override
    public List<TreeNode> levelNodes(int level) {
        List<TreeNode> treeNodes = treeNodeMapper.levelNodes(level);
        return CollectionUtils.isEmpty(treeNodes) ? Collections.emptyList() : treeNodes;
    }

    @Override
    public List<TreeNode> parents(Long id, boolean include, int level) {
        TreeNode node = this.nodeInfo(id);
        if (Objects.isNull(node) || node.getLevel() == ROOT_LEVEL) {
            return Collections.emptyList();
        }
        List<TreeNode> parents = treeNodeMapper.parents(node, include, level);
        return CollectionUtils.isEmpty(parents) ? Collections.emptyList() : parents;
    }

    @Override
    public TreeNode parent(Long id) {
        return treeNodeMapper.parent(id);
    }

    @Transactional
    @Override
    public List<TreeNode> children(Long id, boolean include, int level) {
        TreeNode treeNode = treeNodeService.nodeInfo(id);
        if (Objects.isNull(treeNode)) {
            return Collections.emptyList();
        }
        List<TreeNode> children = treeNodeMapper.children(treeNode, include, level);
        return CollectionUtils.isEmpty(children) ? Collections.emptyList() : children;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TreeNode addNode(Long pid, String nodeName) {
        if (pid < 0) {
            // 找不到父节点，直接添加一层节点
            TreeNode maxRoot = treeNodeMapper.levelMaxOrMinNode(ROOT_LEVEL, true);
            int rootLft = 1 + (Objects.isNull(maxRoot) ? 0 : maxRoot.getRgt());
            int rootRgt = 2 + (Objects.isNull(maxRoot) ? 0 : maxRoot.getRgt());

            TreeNode levelOneNode = TreeNode.builder()
                    .name(nodeName)
                    .lft(rootLft)
                    .rgt(rootRgt)
                    .level(1)
                    .build();

            treeNodeMapper.addNode(levelOneNode);
            return levelOneNode;
        }

        TreeNode pNode = treeNodeMapper.nodeInfo(pid);
        if (Objects.isNull(pNode)) {
            log.info("找不到节点，无法新增，TreeNode.id = {} ", pid);
            return null;
        }

        int newNodeLft = pNode.getRgt();
        int newNodeRgt = newNodeLft + 1;
        int newNodeLevel = pNode.getLevel() + 1;

        TreeNode node = TreeNode.builder()
                .name(nodeName)
                .lft(newNodeLft)
                .rgt(newNodeRgt)
                .level(newNodeLevel)
                .build();

        // 1. update lft
        int lftUpdate = treeNodeMapper.updateLft(newNodeRgt);
        // 2. update rgt
        int rgtUpdate = treeNodeMapper.updateRgt(newNodeLft);

        log.debug("AddNode lftUpdate = {},rgtUpdate = {}", lftUpdate, rgtUpdate);
        // 3. insert node
        treeNodeMapper.addNode(node);
        return node;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeNode(Long id, boolean include) {
        TreeNode node = treeNodeMapper.nodeInfo(id);
        if (Objects.isNull(node)) {
            log.error("removeNode, cannot find node,id = {}", id);
            return;
        }
        treeNodeMapper.removeRange(node.getLft(), node.getRgt(), include);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeChildNodes(Long id) {
        TreeNode node = treeNodeMapper.nodeInfo(id);
        if (Objects.isNull(node)) {
            log.error("removeNode, cannot find node,id = {}", id);
            return;
        }
        treeNodeMapper.removeRange(node.getLft(), node.getRgt(), false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeLevel(int level) {
        if (level <= 0) {
            log.error("removeLevel ,level is  = {}", level);
            return;
        }
        TreeNode levelMin = treeNodeMapper.levelMaxOrMinNode(level, false);
        TreeNode levelMax = treeNodeMapper.levelMaxOrMinNode(level, true);

        if (Objects.isNull(levelMin) || Objects.isNull(levelMax)) {
            log.error("removeLevel,cannot find max or min node in level {}", level);
        }

        treeNodeMapper.removeRange(levelMin.getLft(), levelMax.getRgt(), true);
    }
}