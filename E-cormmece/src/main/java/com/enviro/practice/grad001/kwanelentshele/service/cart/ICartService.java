package com.enviro.practice.grad001.kwanelentshele.service.cart;

import java.math.BigDecimal;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.model.User;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Cart initializeNewCart(User user);
    Cart getCartByUserId(Long userId);

}
