package com.bestbuy.TransactionApp.service;

import com.bestbuy.TransactionApp.dto.OrderResponse;
import com.bestbuy.TransactionApp.dto.ShoppingCartResponse;
import com.bestbuy.TransactionApp.model.Order;
import com.bestbuy.TransactionApp.model.OrderItem;
import com.bestbuy.TransactionApp.model.OrderStatus;
import com.bestbuy.TransactionApp.model.ShoppingCart;
import com.bestbuy.TransactionApp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final StockService stockService;

    public OrderResponse placeOrder(Long userId) {
        ShoppingCartResponse shoppingCartResponse = shoppingCartService.getShoppingCart(userId);
        List<OrderItem> orderItemList = createOrderItems(shoppingCartResponse);
        if(updateStock(orderItemList)) {
            Order order = Order.builder().orderItemList(orderItemList).userId(userId).status(OrderStatus.PLACED).build();
            orderRepository.save(order);
            shoppingCartService.clearShoppingCart(userId);
            return mapOrder(order);
        }
        return null;
    }

    private boolean updateStock(List<OrderItem> orderItemList) {
        for(OrderItem orderItem:orderItemList){
            if(stockService.canDecrementStock(orderItem.getProductId(),orderItem.getQuantity()).isEmpty()){
                return false;
            }
        }
        orderItemList.stream().map(orderItem -> stockService.decrementStock(orderItem.getProductId(),orderItem.getQuantity()));
        return true;
    }

    private List<OrderItem> createOrderItems(ShoppingCartResponse shoppingCartResponse) {
        List<OrderItem> orderItemList = new ArrayList<>();
        shoppingCartResponse.getCartItemList().stream()
                .map(cartItemResponse -> orderItemList.
                        add(OrderItem.builder().
                                productId(cartItemResponse.getProductId())
                                .quantity(cartItemResponse.getQuantity()).build()));

        return orderItemList;
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
        Optional<Order> order = orderRepository.getOrderById(orderId);
        if (order.isPresent())
            return mapOrder(order.get());
        else
            throw new NoSuchElementException("Order with id " + orderId + "doesn't exist");
    }

    public OrderResponse mapOrder(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .createdAt(order.getCreatedAt()).orderItemList(new ArrayList<>(order.getOrderItemList()))
                .build();
    }


}
