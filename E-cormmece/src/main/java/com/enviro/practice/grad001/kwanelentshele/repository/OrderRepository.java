
package com.enviro.practice.grad001.kwanelentshele.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.practice.grad001.kwanelentshele.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

}
