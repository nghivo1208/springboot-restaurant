package com.example.trainee.restaurant.controller;

import com.example.trainee.restaurant.exception.NotFoundException;
import com.example.trainee.restaurant.model.Customers;
import com.example.trainee.restaurant.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/customers")
    public List<Customers> retrieveAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customers retrieveCustomer(@PathVariable int id) {
        Optional<Customers> customers = customerRepository.findById(id);
        if (customers.isEmpty()) {
            throw new NotFoundException("Can not find Customer by id: ");
        }
        return customers.get();
    }

    @PostMapping("/customers")
    public ResponseEntity<Object> createCustomers(@Valid @RequestBody Customers customers) {
        Customers savedCustomers = customerRepository.save(customers);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCustomers.getCustomerId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<Customers> updateCustomer(@PathVariable("id") Integer customerId, @RequestBody Customers customers) {
        Optional<Customers> customerData = customerRepository.findById(customerId);

        if (customerData.isPresent()) {
            Customers _customer = customerData.get();
            _customer.setName(customers.getName());
            _customer.setPhone(customers.getPhone());
//            _customer.setGender(customers.getGender());
            return new ResponseEntity<>(customerRepository.save(_customer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/customers/{id}")
    public void deleteUser(@PathVariable int id) {
        customerRepository.deleteById(id);
    }
}
