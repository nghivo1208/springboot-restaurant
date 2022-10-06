package com.example.trainee.restaurant.controller;

import com.example.trainee.restaurant.exception.NotFoundException;
import com.example.trainee.restaurant.model.Customers;
import com.example.trainee.restaurant.model.Order;
import com.example.trainee.restaurant.model.OrderItems;
import com.example.trainee.restaurant.repository.CustomerRepository;
import com.example.trainee.restaurant.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers/{id}/order")
    public List<Order> retrieveAllOrderOfCustomer(@PathVariable int id) {
        Optional<Customers> customers = customerRepository.findById(id);

        if (customers.isEmpty()) {
            throw new NotFoundException("Can not find order for user id: " + id);
        }
        return customers.get().getOrders();
    }

    @GetMapping("/customers/{customer_id}/order/{order_id}")
    public List<OrderItems> retrieveAllItemsInOrder(@PathVariable("customer_id") Integer customer_id,
                                                    @PathVariable("order_id") Integer order_id){

        Optional<Customers> customers = customerRepository.findById(customer_id);
        Optional<Order> order = orderRepository.findById(order_id);

        if (customers.isPresent() && order.isPresent()){
             return order.get().getOrderItems();
        }else{
            throw new NotFoundException("Can not find customer or order.");
        }
    }

    @PostMapping("/customers/{id}/order")
    public ResponseEntity<Object> addOrderToCustomer(@PathVariable int id, @Valid @RequestBody Order order) {
        Optional<Customers> customers = customerRepository.findById(id);

        if (customers.isEmpty()) {
            throw new NotFoundException("Customer with id does not exist: " + id);
        }

        order.setCustomers(customers.get());
        Order savedOrder = orderRepository.save(order);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrder.getOrderId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/customers/{customer_id}/order/{order_id}")
    public void deleteOrderFromCustomer(@PathVariable("customer_id") int customer_id,
                                        @PathVariable("order_id") int order_id) {
        Optional<Customers> customer = customerRepository.findById(customer_id);
        Optional<Order> order = orderRepository.findById(order_id);

        if (customer.isPresent()) {
            orderRepository.deleteById(order_id);
        } else {
            throw new NotFoundException("Customer does not exist: " + customer_id);
        }
    }


}
