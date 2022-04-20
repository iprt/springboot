package org.iproute.springboot.design.mysqltree.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * TreeNode
 *
 * @author winterfell
 * @since 2022/4/19
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@TableName("tree_node")
public class TreeNode {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String name;
    private int lft;
    private int rgt;
    private int level;
    private int sortKey;
    private Date createTime;
    private Date updateTime;
}
