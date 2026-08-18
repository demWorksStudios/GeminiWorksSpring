package com.saree.order.service;

import com.saree.order.dto.OrderItemRequest;
import com.saree.order.dto.OrderRequest;
import com.saree.order.exception.ResourceNotFoundException;
import com.saree.order.model.Order;
import com.saree.order.model.OrderStatus;
import com.saree.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order1;

    @BeforeEach
    void setUp() {
        order1 = Order.builder()
                .id(1L)
                .orderNumber("SAR-ORD-12345678")
                .customerName("Priya Sharma")
                .customerEmail("priya.sharma@example.com")
                .customerPhone("9876543210")
                .shippingAddress("104 Park Street, Kolkata")
                .orderStatus(OrderStatus.PLACED)
                .totalAmount(18999.0)
                .build();
    }

    @Test
    void testPlaceOrder() {
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        OrderRequest req = OrderRequest.builder()
                .customerName("Priya Sharma")
                .customerEmail("priya.sharma@example.com")
                .customerPhone("9876543210")
                .shippingAddress("104 Park Street, Kolkata")
                .paymentMode("UPI")
                .items(List.of(
                        OrderItemRequest.builder()
                                .sareeId(1L)
                                .sareeName("Royal Crimson Kanjeevaram Silk Saree")
                                .sku("SAR-KAN-RED-001")
                                .quantity(1)
                                .unitPrice(18999.0)
                                .build()
                ))
                .build();

        Order placed = orderService.placeOrder(req);

        assertNotNull(placed);
        assertEquals("Priya Sharma", placed.getCustomerName());
        assertEquals(18999.0, placed.getTotalAmount());
        assertEquals(1, placed.getItems().size());
        assertEquals(OrderStatus.PLACED, placed.getOrderStatus());
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));

        Order found = orderService.getOrderById(1L);

        assertEquals("SAR-ORD-12345678", found.getOrderNumber());
    }

    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(99L));
    }

    @Test
    void testUpdateOrderStatus() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0));

        Order updated = orderService.updateOrderStatus(1L, OrderStatus.CONFIRMED);

        assertEquals(OrderStatus.CONFIRMED, updated.getOrderStatus());
    }
}
