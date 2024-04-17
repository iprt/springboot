package org.iproute.springboot.design.tree.model.tree.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.iproute.springboot.design.tree.model.tree.TreeNode;

/**
 * TreeNode
 *
 * @author devops@kubectl.net
 * @since 2022/4/19
 */
@Setter
@Getter
@TableName("tree_node")
public class MysqlTreeNode extends TreeNode {
    private int lft;
    private int rgt;
}
