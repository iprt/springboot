package org.iproute.springboot.design.mysqltree.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.springboot.design.mysqltree.model.TreeNode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TreeNodeMapper
 *
 * @author winterfell
 * @since 2022 /4/19
 */
@Mapper
@Repository
public interface TreeNodeMapper extends BaseMapper<TreeNode> {

    /**
     * Node info tree node.
     *
     * @param id the id
     * @return the tree node
     */
    TreeNode nodeInfo(@Param("id") Long id);

    /**
     * Level nodes list.
     *
     * @param level the level
     * @return the list
     */
    List<TreeNode> levelNodes(@Param("level") int level);

    /**
     * 查询层级最大或者最小的
     *
     * @param level the level
     * @param max   true: find max ;false: fin min
     * @return the tree node
     */
    TreeNode levelMaxOrMinNode(@Param("level") int level, @Param("max") boolean max);

    /**
     * Parents list;
     *
     * @param node    the node
     * @param include 是否包含自身
     * @param level   查询的层级 自身所在的层数为0层
     * @return the tree node
     */
    List<TreeNode> parents(@Param("node") TreeNode node,
                           @Param("include") boolean include, @Param("level") int level);


    /**
     * 性能问题，单独提取出parent
     * <p>
     * TODO 对于特殊的Children查询也需要提取出来
     *
     * @param id the id
     * @return the tree node
     */
    TreeNode parent(@Param("id") Long id);

    /**
     * Children list.
     *
     * @param parent  the parent
     * @param include 是否包含节点自身
     * @param level   查询以下的层级, 自身所在的层数为0层
     * @return the list
     */
    List<TreeNode> children(@Param("node") TreeNode parent, @Param("include") boolean include, @Param("level") int level);


    /**
     * Add node int.
     *
     * @param node the node
     * @return the int
     */
    int addNode(TreeNode node);


    /**
     * Add nodes int.
     *
     * @param nodes the nodes
     * @return the int
     */
    int addNodes(List<TreeNode> nodes);

    /**
     * 新增节点前的操作，更新需要更新的节点的 lft 的值
     * <p>
     * -- 新增节点后，其余节点的左值更新<br/>
     * -- 所有左值大于等于新节点的右值 全部加2
     *
     * @param newNodeRgt the new node rgt
     * @param count      the count 添加的节点的个数
     * @return the int
     */
    int updateLft(@Param("newNodeRgt") int newNodeRgt, @Param("count") int count);

    /**
     * 新增节点前的操作，更新需要更新的节点的 rgt 的值
     * <p>
     * -- 新增节点后，其余节点的右值更新<br/>
     * -- 所有右值大于等于新节点的左值 全部+2
     *
     * @param newNodeLft the new node lft
     * @param count      the count 添加的节点的个数
     * @return the int
     */
    int updateRgt(@Param("newNodeLft") int newNodeLft, @Param("count") int count);

    /**
     * range remove
     *
     * @param lft     > lft
     * @param rgt     < rgt
     * @param include the include
     * @return the int
     */
    int removeRange(@Param("lft") int lft, @Param("rgt") int rgt, @Param("include") boolean include);


    /**
     * reset.
     */
    void reset();

}
