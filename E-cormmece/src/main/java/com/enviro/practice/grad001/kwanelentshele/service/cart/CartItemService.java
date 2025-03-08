package com.enviro.practice.grad001.kwanelentshele.service.cart;

import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.model.CartItem;
import com.enviro.practice.grad001.kwanelentshele.model.Product;
import com.enviro.practice.grad001.kwanelentshele.repository.CartItemRepository;
import com.enviro.practice.grad001.kwanelentshele.repository.CartRepository;
import com.enviro.practice.grad001.kwanelentshele.service.Product.IProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
    
        CartItem cartItem = cart.getItems().stream()
            .filter(items -> items.getProduct().getId().equals(productId))
            .findFirst()
            .orElse(new CartItem());
    
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQauntity(quantity);
        } else {
            cartItem.setQauntity(cartItem.getQauntity() + quantity);
        }
    
        BigDecimal unitPrice = product.getPrice();
        if (unitPrice == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        cartItem.setUnitPrice(unitPrice); 
        cartItem.setTotalPrice(unitPrice); 
    
        cart.addItems(cartItem);
        cartItemRepository.save(cartItem);
        
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
       Cart cart = cartService.getCart(cartId);
       CartItem itemToRemove = cart.getItems().stream()
       .filter(item -> item.getProduct().getId().equals(productId))
       .findFirst().orElseThrow(() -> new ResourceNotFoundException("We are unlble to find the item you are removing!"));
       cart.removeItemFromCart(itemToRemove);

       cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
     Cart cart = cartService.getCart(cartId);

     cart.getItems().stream()
        .filter(item -> item.getProduct().getId().equals(productId))
        .findFirst()
        .ifPresent(item -> {
            item.setQauntity(quantity);

            BigDecimal unitPrice = item.getProduct().getPrice();
            if (unitPrice == null) {
                throw new IllegalArgumentException("Product price cannot be null");
            }
            item.setUnitPrice(unitPrice); 
            item.setTotalPrice(unitPrice); 
        });

    BigDecimal totalAmount = cart.getItems()
        .stream()
        .map(CartItem::getTotalPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    cart.setTotalAmount(totalAmount);

    cartRepository.save(cart);
}

    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);

        return cart.getItems().stream().filter(item -> item.getProduct().getId().equals(productId))
        .findFirst().orElseThrow(() -> new ResourceNotFoundException("Cart items not found!"));
    }

}
