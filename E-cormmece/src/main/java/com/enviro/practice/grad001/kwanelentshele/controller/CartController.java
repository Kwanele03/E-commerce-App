package com.enviro.practice.grad001.kwanelentshele.controller;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.cart.ICartService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {

        private static final HttpStatus NOT_FOUND = HttpStatus.NOT_FOUND;
        private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

        private final ICartService cartService;

        @GetMapping("/{cartId}/my-cart")
        public ResponseEntity<APIResponse> getCart(@PathVariable Long cartId){
            try {
                Cart cart = cartService.getCart(cartId);
                return ResponseEntity.ok(new APIResponse ("Cart returned sucessfully!", cart));
            } 
            catch (ResourceNotFoundException exception) {
               return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null)); 
            }  
        }
    
        @DeleteMapping("{cartId}/clear")
        public ResponseEntity<APIResponse> clearCart(@PathVariable Long cartId){
            try {
                cartService.clearCart(cartId);
                return ResponseEntity.ok(new APIResponse("Cart Cleared sucessfully!", null));
            } 
            catch (ResourceNotFoundException exception) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse(exception.getMessage(), null));
        }
    }
     
       @GetMapping("{cartId}/cart/totalPrice")
       public ResponseEntity<APIResponse> getTotalAmount(@PathVariable Long cartId){
          try {
              BigDecimal totalPrice = cartService.getTotalPrice(cartId);
             return ResponseEntity.ok(new APIResponse("Total amount returned sucessfull!", totalPrice));
           } 
           catch (ResourceNotFoundException exception) {
              return ResponseEntity.status(NOT_FOUND).body(new APIResponse(exception.getMessage(), null));
        }
    }

}
