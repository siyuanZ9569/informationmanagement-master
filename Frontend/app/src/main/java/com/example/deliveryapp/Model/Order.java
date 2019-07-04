package com.example.deliveryapp.Model;

public class Order {
    private int id, buyerId, deliverymanId, sellerId, status;
    private String position;

    /**
     * Empty Constructor
     */
    public Order() {
    }

    public Order(int id, int buyerId, int deliverymanId, int sellerId, int status, String position) {
        this.id = id;
        this.buyerId = buyerId;
        this.deliverymanId = deliverymanId;
        this.sellerId = sellerId;
        this.status = status;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(int buyerId) {
        this.buyerId = buyerId;
    }

    public int getDeliverymanId() {
        return deliverymanId;
    }

    public void setDeliverymanId(int deliverymanId) {
        this.deliverymanId = deliverymanId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
