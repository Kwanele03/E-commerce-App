package com.enviro.practice.grad001.kwanelentshele.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.enviro.practice.grad001.kwanelentshele.dto.OrderDto;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Order;
import com.enviro.practice.grad001.kwanelentshele.response.APIResponse;
import com.enviro.practice.grad001.kwanelentshele.service.Order.IOrderService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/orders")
public class OrderController {

        private static final HttpStatusCode NOT_FOUND = HttpStatus.NOT_FOUND;
        private static final HttpStatusCode INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

        private final IOrderService orderService;
    
        @PostMapping("/create-order")
        public ResponseEntity<APIResponse> createOrder(@RequestParam Long userId){
            try {
                Order order = orderService.placeOrder(userId);
                return ResponseEntity.ok(new APIResponse("Order created successfully!", order));
            } catch (Exception exception) {
                return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new APIResponse("Fail to create an order!", exception.getMessage()));
            } 
        }
       
        @GetMapping("/{orderId}/order")
        public ResponseEntity<APIResponse> getOrderById(@PathVariable Long orderId){
            try {
                OrderDto order = orderService.getOrder(orderId);
                return ResponseEntity.ok(new APIResponse("Order returned successfully!", order));
            } catch (ResourceNotFoundException exception) {
                return ResponseEntity.status(NOT_FOUND)
                .body(new APIResponse("Error occured!", exception.getMessage()));
            }
        }

        @GetMapping("/{userId}/orders")
        public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId){
             try {
                 List<OrderDto> orders = orderService.getUserOrders(userId);
                  return ResponseEntity.ok(new APIResponse("Order returned successfully!", orders));
                } 
                 catch (ResourceNotFoundException exception) {
                  return ResponseEntity.status(NOT_FOUND)
                   .body(new APIResponse("Error occured!", exception.getMessage()));
                }
        }      
    }
    

