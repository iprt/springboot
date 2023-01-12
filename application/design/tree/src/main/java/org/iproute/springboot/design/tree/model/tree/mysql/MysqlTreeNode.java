package org.iproute.springboot.design.tree.model.tree.mysql;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.iproute.springboot.design.tree.model.tree.TreeNode;

/**
 * TreeNode
 *
 * @author winterfell
 * @since 2022/4/19
 */
@TableName("tree_node")
public class MysqlTreeNode extends TreeNode {
    @Setter
    @Getter
    private int lft;
    @Setter
    @Getter
    private int rgt;
}
