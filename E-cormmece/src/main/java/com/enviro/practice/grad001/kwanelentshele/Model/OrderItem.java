package com.enviro.practice.grad001.kwanelentshele.model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class OrderItem {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

private int quantity;
private BigDecimal price;

@ManyToOne
@JoinColumn(name="order_id")
private Order order;

@ManyToOne
@JoinColumn(name="product_id")
private Product product;

public OrderItem(Order order, Product product, int quantity, BigDecimal price) {
    this.order = order;
    this.product = product;
    this.quantity = quantity;
    this.price = price;
}

public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public int getQuantity() {
    return quantity;
}

public void setQauntity(int quantity) {
    this.quantity = quantity;
}

public BigDecimal getPrice() {
    return price;
}

public void setPrice(BigDecimal price) {
    this.price = price;
}

public Order getOrder() {
    return order;
}

public void setOrder(Order order) {
    this.order = order;
}

public Product getProduct() {
    return product;
}

public void setProduct(Product product) {
    this.product = product;
}

}
