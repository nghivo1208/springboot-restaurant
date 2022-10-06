package com.example.trainee.restaurant.repository;

import com.example.trainee.restaurant.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customers, Integer> {
}
