package com.saree.order.service;

import com.saree.order.dto.OrderItemRequest;
import com.saree.order.dto.OrderRequest;
import com.saree.order.exception.ResourceNotFoundException;
import com.saree.order.model.Order;
import com.saree.order.model.OrderItem;
import com.saree.order.model.OrderStatus;
import com.saree.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order placeOrder(OrderRequest request) {
        String orderNumber = "SAR-ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Order order = Order.builder()
                .orderNumber(orderNumber)
                .customerName(request.getCustomerName())
                .customerEmail(request.getCustomerEmail())
                .customerPhone(request.getCustomerPhone())
                .shippingAddress(request.getShippingAddress())
                .paymentMode(request.getPaymentMode() != null ? request.getPaymentMode() : "UPI")
                .orderStatus(OrderStatus.PLACED)
                .createdAt(LocalDateTime.now())
                .items(new ArrayList<>())
                .totalAmount(0.0)
                .build();

        double total = 0.0;
        for (OrderItemRequest itemReq : request.getItems()) {
            double subtotal = itemReq.getUnitPrice() * itemReq.getQuantity();
            total += subtotal;

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .sareeId(itemReq.getSareeId())
                    .sareeName(itemReq.getSareeName())
                    .sku(itemReq.getSku())
                    .quantity(itemReq.getQuantity())
                    .unitPrice(itemReq.getUnitPrice())
                    .subtotal(subtotal)
                    .build();

            order.getItems().add(item);
        }

        order.setTotalAmount(total);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with order number: " + orderNumber));
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmailIgnoreCase(email);
    }

    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    public void cancelOrder(Long id) {
        Order order = getOrderById(id);
        order.setOrderStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
