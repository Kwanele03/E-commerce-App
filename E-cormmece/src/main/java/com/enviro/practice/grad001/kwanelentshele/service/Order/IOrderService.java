package com.enviro.practice.grad001.kwanelentshele.service.Order;

import java.util.List;

import com.enviro.practice.grad001.kwanelentshele.dto.OrderDto;
import com.enviro.practice.grad001.kwanelentshele.model.Order;

public interface IOrderService {
    
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

}
