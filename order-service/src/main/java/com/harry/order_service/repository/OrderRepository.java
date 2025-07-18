package com.harry.order_service.repository;

import com.harry.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByEventId(String eventId);

    List<Order> findAllByUserId(String userId);

}
