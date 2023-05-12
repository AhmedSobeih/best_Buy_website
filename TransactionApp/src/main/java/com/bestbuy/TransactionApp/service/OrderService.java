package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.OrderResponse;
import com.bestbuy.TransactionApp.exception.OrderExceptionSupplier;
import com.bestbuy.TransactionApp.model.*;
import com.bestbuy.TransactionApp.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final StockService stockService;
    private final OrderExceptionSupplier orderExceptionSupplier;

    @Transactional
    public OrderResponse placeOrder(Long userId) {
        if(shoppingCartService.isShoppingCartEmpty(userId))
            throw orderExceptionSupplier.emptyCart(userId);
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(userId);
        List<OrderItem> orderItemList = createOrderItems(shoppingCart);
        if(updateStock(orderItemList)) {
            Order order = Order.builder().orderItemList(orderItemList).userId(userId).status(OrderStatus.PLACED)
                    .createdAt(LocalDateTime.now()).build();
            orderRepository.save(order);
            shoppingCartService.clearShoppingCart(userId);
            return mapOrder(order);
        }
        return null;
    }

    public OrderResponse cancelOrder(Long orderId) {
        throw new NoSuchElementException("Not implemented yet");
    }

    private boolean updateStock(List<OrderItem> orderItemList) {
        orderItemList.stream().forEach((orderItem ->
                stockService.canDecrementStockOrThrow(orderItem.getProductId(), orderItem.getQuantity())));

        orderItemList.stream().forEach(orderItem ->
                stockService.decrementStock(orderItem.getProductId(),orderItem.getQuantity()));
        return true;
    }

    private List<OrderItem> createOrderItems(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItemList().stream().map(this::buildOrderItem).toList();
    }

    public OrderItem buildOrderItem(CartItem cartItem){
        return OrderItem.builder()
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .price(stockService.getPriceOfProduct(cartItem.getProductId())).build();
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> mapOrder(order)).toList();
    }

    public List<OrderResponse> getAllUserOrders(Long userId) {
        List<Order> orders = orderRepository.getByUserId(userId);
        return orders.stream().map(order -> mapOrder(order)).toList();
    }

    public OrderResponse getOrder(Long orderId) {
        return mapOrder(orderRepository.getOrderById(orderId).orElseThrow(orderExceptionSupplier.notFound(orderId)));
    }

    public OrderResponse deleteOrder(Long orderId) {
        OrderResponse orderResponse = getOrder(orderId);
        orderRepository.deleteById(orderId);
        return orderResponse;
    }

    public OrderResponse mapOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt()).orderItemList(new ArrayList<>(order.getOrderItemList()))
                .build();
    }

}
