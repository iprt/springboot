package org.iproute.springboot.design.tree.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.springboot.design.tree.model.tree.TreeNode;

import java.util.List;

/**
 * TreeNodeReq
 *
 * @author tech@intellij.io
 * @since 2022/4/19
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class TreeNodeReq {
    private String ds;
    private TreeNode parentNode;
    private TreeNode addedNode;
    private List<TreeNode> addedNodes;

    private TreeNode removedNode;
    private Boolean include;

    private String command;
}
