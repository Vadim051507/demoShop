package com.example.demoShop.dto;

public class CartItem {
    private Long productId;
    private String name;
    private int price;
    private String img;
    private int quantity;

    public CartItem() {}

    public CartItem(Long productId, String name, int price, String img) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.img = img;
        this.quantity = 1;
    }

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
