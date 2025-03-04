package com.enviro.practice.grad001.kwanelentshele.dto;

import java.util.List;

public class UserDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<OrderDto> orders;
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<OrderDto> getOrders() {
        return orders;
    }
    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
