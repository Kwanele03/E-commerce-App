package com.enviro.practice.grad001.kwanelentshele.service.cart;

import java.math.BigDecimal;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;

public interface ICartService {

    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
    Long initializeNewCart();
    Cart getCartByUserId(Long userId);

}
