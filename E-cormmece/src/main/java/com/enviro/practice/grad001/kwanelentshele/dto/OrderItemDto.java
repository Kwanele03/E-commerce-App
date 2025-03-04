package com.enviro.practice.grad001.kwanelentshele.dto;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long productId;
    private String productName;
    private int qauntity;
    private BigDecimal price;
    
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQauntity() {
        return qauntity;
    }
    public void setQauntity(int qauntity) {
        this.qauntity = qauntity;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    
}
