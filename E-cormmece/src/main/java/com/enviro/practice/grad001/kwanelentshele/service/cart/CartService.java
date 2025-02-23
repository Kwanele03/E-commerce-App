package com.enviro.practice.grad001.kwanelentshele.service.cart;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.model.CartItem;
import com.enviro.practice.grad001.kwanelentshele.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import com.enviro.practice.grad001.kwanelentshele.repository.CartItemRepository;


@Service
@RequiredArgsConstructor
public class CartService implements ICartService{

  private final CartRepository cartRepository;
  private final CartItemRepository cartItemRepository;
  private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long id){
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }
   

    @Override
    public void clearCart(Long id){
      Cart cart = getCart(id);
      cartItemRepository.deleteAllById(id);
      cart.getItems().clear();
      cartRepository.deleteById(id);
    }
   

    @Override
    public BigDecimal getTotalPrice(Long id){
        Cart cart = getCart(id);
        return cart.getItems().stream().map(CartItem :: getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    @Override
    public Long initializeNewCart(){
      Cart newCart = new Cart();
      Long newCartId = cartIdGenerator.incrementAndGet();
      newCart.setId(newCartId);
      return cartRepository.save(newCart).getId();
    }

}
