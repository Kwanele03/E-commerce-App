 package com.enviro.practice.grad001.kwanelentshele.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.enviro.practice.grad001.kwanelentshele.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

    
}