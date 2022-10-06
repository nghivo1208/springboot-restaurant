package com.example.trainee.restaurant.repository;

import com.example.trainee.restaurant.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}
