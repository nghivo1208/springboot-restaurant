package com.example.trainee.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "Foods")
@Table(name = "foods")
public class Foods {

    @Id
    @GeneratedValue
    @Column(name = "food_id")
    private Integer foodId;
    @Column(name = "food_name")
    private String foodName;
    @Column(name = "price")
    private BigDecimal price;

    @OneToMany(mappedBy = "foods")
    @JsonIgnore
    private List<OrderItems> orderItems;

    public Foods() {
    }

    public Foods(Integer foodId, String foodName, BigDecimal price) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.price = price;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<OrderItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItems> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Foods{" +
                "foodId=" + foodId +
                ", foodName='" + foodName + '\'' +
                ", price=" + price +
                '}';
    }
}
