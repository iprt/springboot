package org.iproute.springboot.design.tree.service.impl;

import com.beust.jcommander.internal.Lists;
import org.iproute.springboot.design.tree.model.tree.TreeNode;
import org.iproute.springboot.design.tree.service.TreeNodeCmdService;
import org.iproute.springboot.design.tree.service.TreeNodeService;
import org.iproute.springboot.design.tree.utils.TreeNodeValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * TreeNodeCommandQueryServiceImpl
 *
 * @author devops@kubectl.net
 * @since 2022/4/20
 */
@Service
public class TreeNodeCmdServiceImpl implements TreeNodeCmdService {


    @Override
    public List<TreeNode> command(TreeNodeService treeNodeService,
                                  String option, String type,
                                  Long id, Long pid, String name,
                                  boolean include, int level) {
        if (TreeNodeValidator.Option.REMOVE.equals(option)) {
            this.remove(treeNodeService, id, include);
            return Collections.emptyList();
        }

        if (TreeNodeValidator.Option.ADD.equals(option)) {
            TreeNode add = this.add(treeNodeService, pid, name);
            return Objects.isNull(add) ? Collections.emptyList() : Lists.newArrayList(add);
        }

        if (TreeNodeValidator.Option.QUERY.equals(option)) {
            return this.query(treeNodeService, type, id, include, level);
        }
        return Collections.emptyList();
    }


    private void remove(TreeNodeService treeNodeService,
                        Long id, boolean include) {
        treeNodeService.removeNode(id, include);
    }


    public TreeNode add(TreeNodeService treeNodeService,
                        Long pid, String name) {
        return treeNodeService.addNode(pid, name);
    }

    public List<TreeNode> query(TreeNodeService treeNodeService,
                                String type, Long id, boolean include, int level) {

        if (TreeNodeValidator.Type.SELF.equals(type)) {
            TreeNode node = treeNodeService.nodeInfo(id);
            return Objects.isNull(node) ? Collections.emptyList() : Lists.newArrayList(node);
        }

        if (TreeNodeValidator.Type.CHILDREN.equals(type)) {
            return treeNodeService.children(id, include, level);
        }

        if (TreeNodeValidator.Type.PARENT.equals(type)) {
            return treeNodeService.parents(id, include, level);
        }

        return Collections.emptyList();

    }


}
