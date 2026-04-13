package com.johnteacher.shoppingcart.service.order;

import com.johnteacher.shoppingcart.dto.OrderDto;
import com.johnteacher.shoppingcart.enums.OrderStatus;
import com.johnteacher.shoppingcart.exceptions.ResourceNotFoundException;
import com.johnteacher.shoppingcart.model.Cart;
import com.johnteacher.shoppingcart.model.Order;
import com.johnteacher.shoppingcart.model.OrderItem;
import com.johnteacher.shoppingcart.model.Product;
import com.johnteacher.shoppingcart.repository.OrderRepository;
import com.johnteacher.shoppingcart.repository.ProductRepository;
import com.johnteacher.shoppingcart.service.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createOrderItems(order, cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setOrderTotalAmount(calculateTotalAmount(orderItemList));
        Order savedOrder = orderRepository.save(order);

        cartService.clearCart(cart.getId()); // clear the cart once order placed

        return savedOrder;
    }

    private Order createOrder(Cart cart) {
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product); // updates the count
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems.stream().map(item -> item.getPrice()
                .multiply(new BigDecimal(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId).map(this :: convertToDto).orElseThrow(
                () -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserUserId(userId);
        return orders.stream().map(this :: convertToDto).toList(); // "::" means "for each order, convertToDto(order)"
    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

}
