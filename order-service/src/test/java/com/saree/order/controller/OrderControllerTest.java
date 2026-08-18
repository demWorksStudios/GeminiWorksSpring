package com.saree.order.controller;

import com.saree.order.dto.OrderItemRequest;
import com.saree.order.dto.OrderRequest;
import com.saree.order.dto.OrderStatusUpdateRequest;
import com.saree.order.model.Order;
import com.saree.order.model.OrderStatus;
import com.saree.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    private Order order1;

    @BeforeEach
    void setUp() {
        order1 = Order.builder()
                .id(1L)
                .orderNumber("SAR-ORD-12345678")
                .customerName("Priya Sharma")
                .customerEmail("priya.sharma@example.com")
                .orderStatus(OrderStatus.PLACED)
                .totalAmount(18999.0)
                .build();
    }

    @Test
    void testPlaceOrder() {
        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(order1);

        OrderRequest req = OrderRequest.builder()
                .customerName("Priya Sharma")
                .customerEmail("priya.sharma@example.com")
                .shippingAddress("104 Park Street, Kolkata")
                .items(List.of(
                        OrderItemRequest.builder()
                                .sareeName("Royal Crimson Kanjeevaram Silk Saree")
                                .sku("SAR-KAN-RED-001")
                                .quantity(1)
                                .unitPrice(18999.0)
                                .build()
                ))
                .build();

        ResponseEntity<Order> response = orderController.placeOrder(req);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SAR-ORD-12345678", response.getBody().getOrderNumber());
    }

    @Test
    void testGetAllOrders() {
        when(orderService.getAllOrders()).thenReturn(List.of(order1));

        ResponseEntity<List<Order>> response = orderController.getAllOrders(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetOrderById() {
        when(orderService.getOrderById(1L)).thenReturn(order1);

        ResponseEntity<Order> response = orderController.getOrderById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Priya Sharma", response.getBody().getCustomerName());
    }

    @Test
    void testUpdateStatus() {
        when(orderService.updateOrderStatus(eq(1L), eq(OrderStatus.CONFIRMED))).thenReturn(order1);

        OrderStatusUpdateRequest req = OrderStatusUpdateRequest.builder()
                .status(OrderStatus.CONFIRMED)
                .build();

        ResponseEntity<Order> response = orderController.updateStatus(1L, req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testCancelOrder() {
        ResponseEntity<Void> response = orderController.cancelOrder(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(orderService, times(1)).cancelOrder(1L);
    }
}
