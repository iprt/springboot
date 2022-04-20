package org.iproute.springboot.design.mysqltree.model.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.iproute.springboot.design.mysqltree.model.TreeNode;

/**
 * TreeNodeReq
 *
 * @author winterfell
 * @since 2022/4/19
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class TreeNodeReq {
    private TreeNode parentNode;
    private TreeNode addedNode;

    private String command;
}
