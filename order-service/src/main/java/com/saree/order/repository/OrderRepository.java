package com.saree.order.repository;

import com.saree.order.model.Order;
import com.saree.order.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByCustomerEmailIgnoreCase(String customerEmail);

    List<Order> findByOrderStatus(OrderStatus orderStatus);
}
