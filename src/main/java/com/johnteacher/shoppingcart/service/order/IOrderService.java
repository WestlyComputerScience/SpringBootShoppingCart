package com.johnteacher.shoppingcart.service.order;

import com.johnteacher.shoppingcart.dto.OrderDto;
import com.johnteacher.shoppingcart.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long order);
    OrderDto getOrderById(Long orderId);
    List<OrderDto> getUserOrders(Long userId);
}
