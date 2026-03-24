package com.example.demoShop.dto;

import java.util.List;

public class OrderRequest {
    private String name;
    private String phone;
    private String address;
    private String deliveryType;
    private String comment;
    private List<CartItem> items;
    private int total;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDeliveryType() {
        return deliveryType;
    }
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public List<CartItem> getItems() {
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}
