package com.enviro.practice.grad001.kwanelentshele.service.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.enviro.practice.grad001.kwanelentshele.dto.OrderDto;
import com.enviro.practice.grad001.kwanelentshele.enums.OrderStatus;
import com.enviro.practice.grad001.kwanelentshele.exceptions.ResourceNotFoundException;
import com.enviro.practice.grad001.kwanelentshele.model.Cart;
import com.enviro.practice.grad001.kwanelentshele.model.Order;
import com.enviro.practice.grad001.kwanelentshele.model.OrderItem;
import com.enviro.practice.grad001.kwanelentshele.model.Product;
import com.enviro.practice.grad001.kwanelentshele.repository.OrderRepository;
import com.enviro.practice.grad001.kwanelentshele.repository.ProductRepository;
import com.enviro.practice.grad001.kwanelentshele.service.cart.CartService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartServive;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {   
       Cart cart = cartServive.getCartByUserId(userId);
       Order order = creatOrder(cart);
       List<OrderItem> orderItems = createOrderItems(order, cart);
       order.setOrderItem(new HashSet<>(orderItems));
       order.setTotalAmount(calculateTotalAmount(orderItems));
       Order savedOrder = orderRepository.save(order);
       cartServive.clearCart(cart.getId());
       return savedOrder;
    }

   private Order creatOrder(Cart cart){
    Order order = new Order();

    order.setUser(cart.getUser());
    order.setOrderStatus(OrderStatus.PENDING);
    order.setOrderDate(LocalDate.now());
    return order;
   }
    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQauntity());
            productRepository.save(product);

            return new OrderItem(
                order,
                product,
                cartItem.getQauntity(),
                cartItem.getUnitPrice()
            );

        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems){
        return orderItems.stream()
        .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal :: add);
    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
        .map(this :: convertToDto)
        .orElseThrow(() -> new ResourceNotFoundException ("Order not found!"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId){
       List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this :: convertToDto).toList();
    }

    private OrderDto convertToDto(Order order){
        return modelMapper.map(order, OrderDto.class);
    }

}
