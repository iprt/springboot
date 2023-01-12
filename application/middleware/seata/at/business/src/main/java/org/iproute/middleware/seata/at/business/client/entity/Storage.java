package org.iproute.middleware.seata.at.business.client.entity;

public class Storage {
    private Integer storageId;
    private String goodsCode;
    private Integer quantity;

    public Storage() {

    }

    public Storage(Integer storageId, String goodsCode, Integer quantity) {
        this.storageId = storageId;
        this.goodsCode = goodsCode;
        this.quantity = quantity;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
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

    @Override
    public String toString() {
        return "Storage{" +
                "storageId=" + storageId +
                ", goodsCode='" + goodsCode + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
