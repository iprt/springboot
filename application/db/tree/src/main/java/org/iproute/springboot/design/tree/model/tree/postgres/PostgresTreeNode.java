package org.iproute.springboot.design.tree.model.tree.postgres;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.iproute.springboot.design.tree.model.tree.TreeNode;

/**
 * PostgresTreeNode
 *
 * @author tech@intellij.io
 * @since 2023/1/7
 */
@Setter
@Getter
@TableName("tree_node")
public class PostgresTreeNode extends TreeNode {

    private long pid;
}
