package org.iproute.middleware.seata.at.business.client.entity;

/**
 * @author winterfell
 */
public class Points {
    private Integer pointsId;
    private String username;
    private Integer quantity;

    public Points() {
    }

    public Points(Integer pointsId, String username, Integer quantity) {
        this.pointsId = pointsId;
        this.username = username;
        this.quantity = quantity;
    }

    public Integer getPointsId() {
        return pointsId;
    }

    public void setPointsId(Integer pointsId) {
        this.pointsId = pointsId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Points{" +
                "pointsId=" + pointsId +
                ", username='" + username + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
