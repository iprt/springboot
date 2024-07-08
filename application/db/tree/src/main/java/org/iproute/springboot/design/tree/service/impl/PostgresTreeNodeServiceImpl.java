package org.iproute.springboot.design.tree.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iproute.springboot.design.tree.config.AtxUtils;
import org.iproute.springboot.design.tree.mapper.PostgresTreeNodeMapper;
import org.iproute.springboot.design.tree.model.tree.TreeNode;
import org.iproute.springboot.design.tree.model.tree.postgres.PostgresTreeNode;
import org.iproute.springboot.design.tree.service.TreeNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * PostgresNodeServiceImpl
 *
 * @author devops@kubectl.net
 * @since 2022/4/19
 */
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service("postgresTreeNodeService")
@Slf4j
public class PostgresTreeNodeServiceImpl implements TreeNodeService {
    private final AtxUtils atxUtils;
    private final PostgresTreeNodeMapper postgresTreeNodeMapper;

    @Override
    public TreeNode nodeInfo(long id) {
        return postgresTreeNodeMapper.nodeInfo(id);
    }

    @Override
    public List<TreeNode> levelNodes(int level) {
        return returnTreeNodeList(
                postgresTreeNodeMapper.levelNodes(level)
        );
    }

    @Override
    public List<TreeNode> parents(Long id, boolean include, int level) {
        return returnTreeNodeList(
                postgresTreeNodeMapper.parents(id, include, level)
        );
    }

    @Override
    public TreeNode parent(Long id) {
        return postgresTreeNodeMapper.parent(id);
    }

    @Override
    public List<TreeNode> children(Long id, boolean include, int level) {
        return returnTreeNodeList(
                postgresTreeNodeMapper.children(id, include, level)
        );
    }

    @Override
    public TreeNode addNode(Long pid, String nodeName) {
        TreeNodeService treeNodeService = atxUtils.getAtx().get().getBean(TreeNodeService.class, "postgresTreeNodeService");

        // 找不到父节点
        if (Objects.isNull(pid) || pid < 0) {
            return postgresTreeNodeMapper.addNode(0L, nodeName, ROOT_LEVEL);
        }
        TreeNode pNode = treeNodeService.nodeInfo(pid);
        if (Objects.isNull(pNode)) {
            log.error("找不到父节点的信息,pid = {}", pid);
            return null;
        }
        return postgresTreeNodeMapper.addNode(pid, nodeName, pNode.getLevel() + 1);
    }

    @Override
    public List<TreeNode> addNodes(Long pid, List<String> nodeNames) {
        TreeNodeService treeNodeService = atxUtils.getAtx().get().getBean(TreeNodeService.class, "postgresTreeNodeService");

        TreeNode pNode = treeNodeService.nodeInfo(pid);
        if (Objects.isNull(pNode)) {
            log.error("找不到父节点的信息,pid = {}", pid);
            return Collections.emptyList();
        }

        List<PostgresTreeNode> nodes = nodeNames.stream()
                .map(name -> {
                    PostgresTreeNode node = new PostgresTreeNode();
                    node.setPid(pid);
                    node.setName(name);
                    node.setLevel(pNode.getLevel() + 1);
                    return node;
                })
                .collect(Collectors.toList());
        return returnTreeNodeList(
                postgresTreeNodeMapper.addNodes(nodes)
        );
    }

    @Override
    public int removeNode(Long id, boolean include) {
        TreeNodeService treeNodeService = atxUtils.getAtx().get().getBean(TreeNodeService.class, "postgresTreeNodeService");
        TreeNode cNode = treeNodeService.nodeInfo(id);
        if (Objects.isNull(cNode)) {
            log.error("找不到节点，treeNode.id = {}", id);
            return 0;
        }

        return postgresTreeNodeMapper.removeNodes(id, include);
    }

    @Override
    public int removeLevel(int level) {
        return postgresTreeNodeMapper.removeLevel(level);
    }

    @Override
    public void reset() {
        postgresTreeNodeMapper.reset();
    }
}