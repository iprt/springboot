package org.iproute.middleware.seata.at.business.client.entity;

public class Order {
    private Integer orderId;
    private String username;
    private Integer points;
    private String goodsCode;
    private Integer quantity;
    private Float amount;

    public Order() {

    }

    public Order(String username, Integer points, String goodsCode, Integer quantity, Float amount) {
        this.username = username;
        this.points = points;
        this.goodsCode = goodsCode;
        this.quantity = quantity;
        this.amount = amount;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", username='" + username + '\'' +
                ", points=" + points +
                ", goodsCode='" + goodsCode + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                '}';
    }
}

