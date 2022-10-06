package com.example.trainee.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Customer")
public class Customers {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Integer customerId;
    private String name;
    private Integer phone;
//    private Gender gender;

    @OneToMany(mappedBy = "customers")
    @JsonIgnore
    private List<Order> orders;

    public Customers() {
    }

    public Customers(Integer customerId, String name, Integer phone) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
//        this.gender = gender;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    //    public Gender getGender() {
//        return gender;
//    }
//
//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", phone=" + phone +
//                ", gender=" + gender +
                '}';
    }
}
