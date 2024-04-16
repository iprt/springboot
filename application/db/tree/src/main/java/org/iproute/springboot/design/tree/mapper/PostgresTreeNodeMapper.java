package org.iproute.springboot.design.tree.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.iproute.springboot.design.tree.model.tree.postgres.PostgresTreeNode;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TreeNodeMapper
 *
 * @author devops@kubectl.net
 * @since 2022 /4/19
 */
@Repository
@Mapper
@DS("postgres")
public interface PostgresTreeNodeMapper extends BaseMapper<PostgresTreeNode> {


    /**
     * Node info tree node.
     *
     * @param id the id
     * @return the tree node
     */
    PostgresTreeNode nodeInfo(@Param("id") Long id);

    /**
     * Level nodes list.
     *
     * @param level the level
     * @return the list
     */
    List<PostgresTreeNode> levelNodes(int level);

    /**
     * 查询父节点
     *
     * @param id      id
     * @param include include
     * @param level   level
     * @return the list
     */
    List<PostgresTreeNode> parents(@Param("id") Long id, @Param("include") boolean include, @Param("level") int level);


    /**
     * Parent postgres tree node.
     *
     * @return the postgres tree node
     */
    PostgresTreeNode parent(@Param("id") Long id);


    /**
     * 查询子节点
     *
     * @param id      id
     * @param include include
     * @param level   level
     * @return the list
     */
    List<PostgresTreeNode> children(@Param("id") Long id, @Param("include") boolean include, @Param("level") int level);

    /**
     * 新增一个节点
     *
     * @param pid  the pid
     * @param name the name
     * @return the postgres tree node
     */
    PostgresTreeNode addNode(@Param("pid") Long pid, @Param("name") String name, @Param("level") int level);


    /**
     * Add nodes list.
     *
     * @param nodes the nodes
     * @return the list
     */
    List<PostgresTreeNode> addNodes(List<PostgresTreeNode> nodes);


    /**
     * 递归删除节点
     *
     * @param id      the id
     * @param include the include
     * @return the int
     */
    int removeNodes(@Param("id") Long id, @Param("include") boolean include);


    /**
     * 递归删除层级
     *
     * @param level the level
     * @return the int
     */
    int removeLevel(@Param("id") Integer level);

    /**
     * 清空所有
     */
    void reset();
}
