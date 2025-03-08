package com.enviro.practice.grad001.kwanelentshele.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.model.User;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.cart.ICartItemService;
import com.enviro.practice.grad001.kwanelentshele.service.cart.ICartService;
import com.enviro.practice.grad001.kwanelentshele.service.user.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    
        private static final HttpStatusCode NOT_FOUND = HttpStatus.NOT_FOUND;

        private final ICartItemService cartItemService;
        private final ICartService cartService;
        private final IUserService userService;
 
        @PostMapping("/item/add")
        public ResponseEntity<APIResponse> addItemToCart(@RequestParam Long productId, @RequestParam int quantity) {
            try {
                User user = userService.getUserById(2L);
                Cart cart = cartService.initializeNewCart(user);
                cartItemService.addItemToCart(cart.getId(), productId, quantity);
                return ResponseEntity.ok(new APIResponse("Item added successfully!", null));
            } catch (ResourceNotFoundException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
            }
        }
    
    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove-item")
    public ResponseEntity<APIResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new APIResponse("Item removed successfully!", null));
        } 
        catch (ResourceNotFoundException exception) {
           return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
        }
    }

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<APIResponse> updateItemQuantity(@PathVariable Long cartId, @PathVariable Long itemId, @RequestBody Integer quantity){
         try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new APIResponse ("Qauntity updated successfull!", null));
        } 
        catch (ResourceNotFoundException exception) {
            return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
        }
    }

}


