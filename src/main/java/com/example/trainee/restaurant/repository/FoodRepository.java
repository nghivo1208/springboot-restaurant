package com.example.trainee.restaurant.repository;

import com.example.trainee.restaurant.model.Foods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Foods, Integer> {
}
