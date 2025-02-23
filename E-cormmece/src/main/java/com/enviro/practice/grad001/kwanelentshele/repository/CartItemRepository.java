package com.enviro.practice.grad001.kwanelentshele.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    void deleteAllById(Long cartid);
    void save(Cart cart);


}
