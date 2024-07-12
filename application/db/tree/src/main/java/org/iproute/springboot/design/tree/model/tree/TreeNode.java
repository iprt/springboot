package org.iproute.springboot.design.tree.model.tree;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * TreeNode
 *
 * @author tech@intellij.io
 * @since 2023/1/7
 */
@Data
public class TreeNode {
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    protected String name;
    protected Integer level;
    protected Integer sortKey;
    protected Date createTime;
    protected Date updateTime;

    @TableField(exist = false)
    protected String ds;
}
