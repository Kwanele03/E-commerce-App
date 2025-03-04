package com.enviro.practice.grad001.kwanelentshele.dto;

import java.math.BigDecimal;
import java.util.Set;

public class CartDto {

    private Long cartId;
    private BigDecimal totalAmount;
    private Set<CartItemDto> items;

    public Long getCartId() {
        return cartId;
    }
    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Set<CartItemDto> getItems() {
        return items;
    }
    public void setItems(Set<CartItemDto> items) {
        this.items = items;
    }
}
