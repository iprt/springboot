package org.iproute.middleware.seata.at.points.dao;


import org.apache.ibatis.annotations.Mapper;
import org.iproute.middleware.seata.at.points.entity.Points;
import org.springframework.stereotype.Repository;

/**
 * The interface Points dao.
 */
@Repository
@Mapper
public interface PointsDAO {
    /**
     * Find by username points.
     *
     * @param username the username
     * @return the points
     */
    Points findByUsername(String username);

    /**
     * Update.
     *
     * @param points the points
     */
    void update(Points points);
}
