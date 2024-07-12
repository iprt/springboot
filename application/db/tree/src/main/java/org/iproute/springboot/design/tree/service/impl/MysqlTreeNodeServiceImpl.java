package org.iproute.springboot.design.tree.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.design.tree.config.AtxUtils;
import org.iproute.springboot.design.tree.mapper.MysqlTreeNodeMapper;
import org.iproute.springboot.design.tree.model.tree.TreeNode;
import org.iproute.springboot.design.tree.model.tree.mysql.MysqlTreeNode;
import org.iproute.springboot.design.tree.service.TreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * MysqlTreeNodeServiceImpl
 *
 * @author tech@intellij.io
 * @since 2022/4/19
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("mysqlTreeNodeService")
@Slf4j
public class MysqlTreeNodeServiceImpl implements TreeNodeService {
    private final AtxUtils atxUtils;
    private final MysqlTreeNodeMapper mysqlTreeNodeMapper;

    @Override
    public MysqlTreeNode nodeInfo(long id) {
        return mysqlTreeNodeMapper.nodeInfo(id);
    }

    @Override
    public List<TreeNode> levelNodes(int level) {
        return returnTreeNodeList(mysqlTreeNodeMapper.levelNodes(level));
    }

    @Override
    public List<TreeNode> parents(Long id, boolean include, int level) {
        MysqlTreeNode node = this.nodeInfo(id);
        if (Objects.isNull(node) || node.getLevel() == ROOT_LEVEL) {
            return Collections.emptyList();
        }
        List<MysqlTreeNode> parents = mysqlTreeNodeMapper.parents(node, include, level);
        return returnTreeNodeList((parents));
    }

    @Override
    public TreeNode parent(Long id) {
        return mysqlTreeNodeMapper.parent(id);
    }

    @Transactional
    @Override
    public List<TreeNode> children(Long id, boolean include, int level) {
        TreeNodeService treeNodeService = atxUtils.getAtx().get().getBean(TreeNodeService.class, "mysqlTreeNodeService");
        TreeNode treeNode = treeNodeService.nodeInfo(id);
        if (Objects.isNull(treeNode)) {
            return Collections.emptyList();
        }
        List<MysqlTreeNode> children = mysqlTreeNodeMapper.children((MysqlTreeNode) treeNode, include, level);
        return returnTreeNodeList(children);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TreeNode addNode(Long pid, String nodeName) {
        if (Objects.isNull(pid) || pid < 0) {
            // 找不到父节点，直接添加一层节点
            MysqlTreeNode maxRoot = mysqlTreeNodeMapper.levelMaxOrMinNode(ROOT_LEVEL, true);
            int rootLft = 1 + (Objects.isNull(maxRoot) ? 0 : maxRoot.getRgt());
            int rootRgt = 2 + (Objects.isNull(maxRoot) ? 0 : maxRoot.getRgt());

            // MysqlTreeNode levelOneNode = MysqlTreeNode.builder()
            //         .name(nodeName)
            //         .lft(rootLft)
            //         .rgt(rootRgt)
            //         .level(1)
            //         .build();
            MysqlTreeNode levelOneNode = new MysqlTreeNode();
            levelOneNode.setName(nodeName);
            levelOneNode.setLevel(1);

            levelOneNode.setLft(rootLft);
            levelOneNode.setRgt(rootRgt);


            mysqlTreeNodeMapper.addNode(levelOneNode);
            return levelOneNode;
        }

        MysqlTreeNode pNode = mysqlTreeNodeMapper.nodeInfo(pid);
        if (Objects.isNull(pNode)) {
            log.info("找不到节点，无法新增，TreeNode.id = {} ", pid);
            return null;
        }

        int newNodeLft = pNode.getRgt();
        int newNodeRgt = newNodeLft + 1;
        int newNodeLevel = pNode.getLevel() + 1;

        // MysqlTreeNode node = MysqlTreeNode.builder()
        //         .name(nodeName)
        //         .lft(newNodeLft)
        //         .rgt(newNodeRgt)
        //         .level(newNodeLevel)
        //         .build();
        MysqlTreeNode node = new MysqlTreeNode();
        node.setName(nodeName);

        node.setLft(newNodeLft);
        node.setRgt(newNodeRgt);
        node.setLevel(newNodeLevel);

        // 1. update lft
        int lftUpdate = mysqlTreeNodeMapper.updateLft(newNodeRgt, 1);
        // 2. update rgt
        int rgtUpdate = mysqlTreeNodeMapper.updateRgt(newNodeLft, 1);

        log.debug("AddNode lftUpdate = {},rgtUpdate = {}", lftUpdate, rgtUpdate);
        // 3. insert node
        mysqlTreeNodeMapper.addNode(node);
        return node;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TreeNode> addNodes(Long pid, List<String> nodeNames) {
        if (Objects.isNull(pid) || pid < 0) {
            // 找不到父节点，直接添加一层节点
            throw new RuntimeException("pid error");
        }
        MysqlTreeNode pNode = mysqlTreeNodeMapper.nodeInfo(pid);
        if (Objects.isNull(pNode)) {
            log.info("pNode error，TreeNode.id = {} ", pid);
            throw new RuntimeException("pNode error");
        }

        if (CollectionUtils.isEmpty(nodeNames)) {
            throw new RuntimeException("nodes error");
        }

        int pRgt = pNode.getRgt();
        int newNodeLevel = pNode.getLevel() + 1;
        final int count = nodeNames.size();

        // List<MysqlTreeNode> newNodes = IntStream.range(0, count).mapToObj(
        //         i -> MysqlTreeNode.builder()
        //                 .name(nodeNames.get(i))
        //                 .lft(pRgt + i * 2).rgt(pRgt + i * 2 + 1)
        //                 .level(newNodeLevel)
        //                 .build()
        // ).collect(Collectors.toList());


        List<MysqlTreeNode> newNodes = IntStream.range(0, count).mapToObj(
                i -> {
                    MysqlTreeNode node = new MysqlTreeNode();
                    node.setName(nodeNames.get(i));
                    node.setLft(pRgt + i * 2);
                    node.setRgt(pRgt + i * 2 + 1);
                    node.setLevel(newNodeLevel);

                    return node;
                }
        ).collect(Collectors.toList());

        // 添加多个节点的话取第一个节点
        MysqlTreeNode first = newNodes.get(0);

        // 1. update lft
        mysqlTreeNodeMapper.updateLft(first.getRgt(), count);

        // 2. update rgt
        mysqlTreeNodeMapper.updateRgt(first.getLft(), count);

        // 3. insert nodes
        mysqlTreeNodeMapper.addNodes(newNodes);
        return returnTreeNodeList(newNodes);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int removeNode(Long id, boolean include) {
        MysqlTreeNode node = mysqlTreeNodeMapper.nodeInfo(id);
        if (Objects.isNull(node)) {
            log.error("removeNode, cannot find node,id = {}", id);
            return 0;
        }
        return mysqlTreeNodeMapper.removeRange(node.getLft(), node.getRgt(), include);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int removeLevel(int level) {
        if (level <= 0) {
            log.error("removeLevel ,level is  = {}", level);
            return 0;
        }
        MysqlTreeNode levelMin = mysqlTreeNodeMapper.levelMaxOrMinNode(level, false);
        MysqlTreeNode levelMax = mysqlTreeNodeMapper.levelMaxOrMinNode(level, true);

        if (Objects.isNull(levelMin) || Objects.isNull(levelMax)) {
            log.error("removeLevel,cannot find max or min node in level {}", level);
        }

        return mysqlTreeNodeMapper.removeRange(levelMin.getLft(), levelMax.getRgt(), true);
    }

    @Override
    public void reset() {
        log.info("reset tree");
        mysqlTreeNodeMapper.reset();
    }
}