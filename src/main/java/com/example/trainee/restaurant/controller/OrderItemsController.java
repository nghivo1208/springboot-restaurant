package com.example.trainee.restaurant.controller;

import com.example.trainee.restaurant.exception.NotFoundException;
import com.example.trainee.restaurant.model.Order;
import com.example.trainee.restaurant.model.OrderItems;
import com.example.trainee.restaurant.repository.OrderItemsRepository;
import com.example.trainee.restaurant.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Optional;

@RestController
public class OrderItemsController {
    private final OrderItemsRepository orderItemsRepository;
    private final OrderRepository orderRepository;

    public OrderItemsController(OrderItemsRepository orderItemsRepository, OrderRepository orderRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/order/{order_id}/order-items")
    public ResponseEntity<Object> addOrderItemToOrder(@PathVariable int id, @Valid @RequestBody OrderItems orderItems) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            throw new NotFoundException("Order with id does not exist: " + id);
        }

        orderItems.setOrder(order.get());
        OrderItems savedOrderItems = orderItemsRepository.save(orderItems);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrderItems.getOrderItemId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/order/{order_id}/order-items/{order_items_id}")
    public void deleteItemInOrder(@PathVariable("order_id") int order_id,
                                  @PathVariable("order_items_id") int order_items_id) {
        Optional<Order> order = orderRepository.findById(order_id);
        Optional<OrderItems> orderItems = orderItemsRepository.findById(order_items_id);

        if (order.isPresent() && orderItems.isPresent()) {
            orderItemsRepository.deleteById(order_items_id);
        } else {
            throw new NotFoundException("Order does not exist: " + order_id);
        }
    }

    @PutMapping("/order/{order_id}/order-items/{order_items_id}")
    public ResponseEntity<OrderItems> updateOrderItem(@PathVariable("order_id") Integer order_id,
                                                      @PathVariable("order_items_id") Integer order_items_id,
                                                      @RequestBody OrderItems orderItems) {
        Optional<Order> order = orderRepository.findById(order_id);
        Optional<OrderItems> orderItemsData = orderItemsRepository.findById(order_items_id);

        if (order.isPresent() && orderItemsData.isPresent()) {
            OrderItems _orderItems = orderItemsData.get();
            _orderItems.setOrder(orderItems.getOrder());
            _orderItems.setQuantity(orderItems.getQuantity());
            return new ResponseEntity<>(orderItemsRepository.save(_orderItems), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
