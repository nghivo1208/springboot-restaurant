package com.example.trainee.restaurant.controller;

import com.example.trainee.restaurant.exception.NotFoundException;
import com.example.trainee.restaurant.model.Foods;
import com.example.trainee.restaurant.repository.FoodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class FoodController {
    private final FoodRepository foodRepository;

    public FoodController(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping("/foods")
    public List<Foods> retrieveAllFood() {
        return foodRepository.findAll();
    }

    @GetMapping("/food/{id}")
    public Foods retrieveFood(@PathVariable int id) {
        Optional<Foods> foods = foodRepository.findById(id);
        if (foods.isEmpty()) {
            throw new NotFoundException("Can not find user by id: " + id);
        }
        return foods.get();
    }

    @PostMapping("/foods")
    public ResponseEntity<Object> createFood(@Valid @RequestBody Foods foods) {
        Foods savedFoods = foodRepository.save(foods);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedFoods.getFoodId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/foods/{id}")
    public ResponseEntity<Foods> updateFood(@PathVariable("id") Integer foodId, @RequestBody Foods food) {
        Optional<Foods> foodData = foodRepository.findById(foodId);

        if (foodData.isPresent()) {
            Foods _food = foodData.get();
            _food.setFoodName(food.getFoodName());
            _food.setPrice(food.getPrice());
            return new ResponseEntity<>(foodRepository.save(_food), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/foods/{id}")
    public void deleteFood(@PathVariable int id) {
        foodRepository.deleteById(id);
    }

    //add food
}
